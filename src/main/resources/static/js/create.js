/**
 * Created by jecyhw on 16-11-5.
 */
$(document).ready(function () {
    var id = '', $picture_form = $("#picture_form"), $info_form = $("#info_form"), preview = $('.preview')
        , progress = $('div.progress'), filename = $('#filename'), uploader, type = $(document.body).attr('type');
    $("#info_submit").click(function () {
        var $it = $(this);
        $.ajax({
            url: $info_form.attr("action"),
            type: 'post',
            dataType: 'json',
            data: $info_form.find(':input').serializeArray(),
            beforeSend: function () {
                $it.addClass('disabled');
                $('.has-error').removeClass('has-error');
            },
            success: function (data) {// 返回记录的信息,包括id
                if (data.code == 200) {
                    if (id.length == 0) {
                        //添加基本信息成功,id赋值
                        id = data.message.id;
                        $('#id').val(id);
                        pageAlert('添加成功', 'success');
                    } else {
                        pageAlert('修改成功', 'success');
                    }
                    modeSwitch();
                } else {
                    $.each(data.message, function (i, fe) {
                        $('#' + fe.field).parent().addClass('has-error');
                        pageAlert(fe.message, 'danger');
                    });
                }
            },
            complete: function () {
                $it.removeClass('disabled');
            }
        });
        return false;
    });

    //中文学名输入失去焦点并且文本修改事件,查看是否存在该名字的虫害数据
    $('#chineseName').change(function () {
        if (id.length == 0) {//插入状态
            var $it = $(this);
            var v = $it.val().trim();
            if (v.length > 0) {
                $.get(addBasePath(type + '/query?name=' + v), function (data) {
                    if (data.code == 200) {
                        $('#info_reset').click();
                        var entity = data.message;
                        //填写基本信息
                        for (var field in entity) {
                            $info_form.find("#" + field).val(entity[field]);
                        }
                        //更新id
                        id = entity.id, $('#pictures').html('');
                        if (entity.pictures && entity.pictures.length > 0) {
                            //生成图片信息表格
                            $.each(entity.pictures, function (i, picture) {
                                createColumnByPicture(picture);
                            })
                        }
                        pageAlert('中文学名的记录已存在', 'info');
                        modeSwitch();
                    } else {
                        $it.val(v);
                    }
                });
            }
        }
    });

    //重置表单,包括基本信息、图片信息,全部清除
    $('#info_reset').click(function () {
        $info_form.find(":input:visible").val('');
        $picture_form.find(":input:visible").val('');
        $("#pictures").html('');
        $.each(uploader.files, function (i, file) {
            uploader.removeFile(file);
        });
        id = '', $('#id').val(''), progress.hide(), filename.html(''), preview.hide();
        modeSwitch();
    });

    //删除整条记录
    $('#info_del').click(function () {
        if (id.length > 0) {
            $.post(addBasePath(type + '/delete/' + id), { '_csrf': $('input[name=_csrf]').val() }, function (data) {
                pageAlert(data.message, 'success');
                $('#info_reset').click();
            });
        }
    });

    uploader = new plupload.Uploader({
        runtimes : 'html5,flash,silverlight,html4',
        browse_button : 'file',
        container: document.getElementById('picture_form'),
        url : addBasePath(type + '/uploadFile'),
        flash_swf_url : addBasePath('plugin/plupload/Moxie.swf'),
        silverlight_xap_url : addBasePath('plugin/plupload/Moxie.xap'),
        multi_selection: false,
        file_data_name: 'file',
        filters : {
            max_file_size : '20mb',
            mime_types: [
                {title : "Image files", extensions : "jpg,gif,png"}
            ]
        },
        init: {
            PostInit: function() {
                progress.hide(), filename.html(''), preview.hide();
                $('#picture_submit').click(function() {
                    if (filename.find('div').attr('id')) {
                        progress.show(), uploader.start();
                    } else {
                        filename.html('<div class="alert alert-danger">请先选择上传图片</div>'), progress.hide();
                    }
                    return false;
                });
            },

            FilesAdded: function(up, files) {
                //保证每次只上传一个文件
                uniqueFile();//在previewPicture前面调用
                plupload.each(files, function(file) {
                    filename.html('<div id="' + file.id + '" class="alert alert-info">' + file.name + ' (' + plupload.formatSize(file.size) + ')</div>');
                    previewPicture(file);
                });
            },

            BeforeUpload: function (up, files) {
                $picture_form.find(":input:visible").prop("disabled", true);
                uploader.setOption('url', addBasePath(type + '/uploadFile/') + id);
                uploader.setOption('params', {
                    'title': $('#title').val(),
                    '_csrf': $('input[name=_csrf]').val()
                });
            },

            UploadProgress: function(up, file) {
                var percent = file.percent + '%';
                progress.find('.progress-bar').css('width', percent).html(percent)
            },

            Error: function(up, err) {
                uniqueFile();
                filename.html('<div class="alert alert-danger">错误 #' + err.code + ': ' + err.message + '</div>');
                $picture_form.find(":input:visible").val('').prop("disabled", false);
            },

            FileUploaded: function (up, file, result) {
                var data = eval('(' + result.response + ')');
                if (data.code == 200) {
                    createColumnByPicture(data.message);
                } else {
                    //图片保存失败,移除图片
                    uploader.removeFile(file);
                    pageAlert(data.message, 'danger');
                    progress.fadeOut('slow');
                }
                filename.html(''), preview.hide();
                $picture_form.find(":input:visible").val('').prop("disabled", false);
            }
        }
    });
    uploader.init();
    modeSwitch();

    //保证每次只上传一次文件
    function uniqueFile() {
        var fileId = filename.find('div').attr('id');
        if (fileId) {
            uploader.removeFile(fileId);
        }
        progress.hide(), preview.hide();
    }

    //模式切换
    function modeSwitch() {
        var add = $('.add-mode'), remove = $('.edit-mode'), tmp, mode = '编辑模式';
        if (id.length == 0) {//根据id来切换模式,id存在编辑模式
            tmp = remove, remove = add, add = tmp, mode = '创建模式';
        }
        if (add.is(":hidden")) {
            return;
        }

        add.fadeOut('slow', function () {
            remove.fadeIn('slow');
        });

        pageAlert('模式切换成功,您已进入' + mode, 'success');

        //是否启用上传表单
        $picture_form.find(":input:visible").prop("disabled", id.length ? false : true);
        var $infoDel = $('#info_del');
        id.length ? $infoDel.fadeIn('slow') : $infoDel.fadeOut('slow');
    }
    //展示图片
    function createColumnByPicture(picture) {
        var parent = $('<div></div>');
        var del = $('<button></button>')
            .addClass("btn btn-danger btn-xs")
            .html('<span class="glyphicon glyphicon-remove-sign"></span>删除');
        var caption = parent.clone()
            .addClass('caption')
            .html(picture.title ? picture.title : picture.name)
            .append(del);
        var href = addBasePath('picture/' + picture.fileId);
        var img = $('<img src="' + href + '"/>').css('height', '100px');
        var box = $('<a></a>')
            .attr('href', href)
            .append(img);
        var thumbnail = parent.clone()
            .addClass('thumbnail')
            .append(box)
            .append(caption);

        var list = $('#pictures');
        if (list.children('div.col-xs-3').length % 4 == 0) {
            list.append(parent.clone().addClass('clearfix'));
        }

        parent.addClass('col-xs-3')
            .append(thumbnail)
            .appendTo(list)
            .hide()
            .fadeIn('slow');
        //点击删除
        del.click(function () {
            //先删除虫害记录里的图片
            $.post(addBasePath(type + '/delete/' + id + '/picture/' + picture.fileId), { '_csrf': $('input[name=_csrf]').val() }, function (data) {
                if (data.code == 200) {
                    pageAlert(data.message, 'success');
                    //再删除图片文件
                    $.post(addBasePath('picture/delete/' + picture.fileId), { '_csrf': $('input[name=_csrf]').val() }, function (data) {
                        pageAlert(data.message, 'success');
                        parent.fadeOut('slow', function () {
                            parent.remove();
                            list.find('.clearfix').remove();
                            var child = list.children('div.col-xs-3');
                            for (var i = 3; i < child.length; i += 4) {
                                child.eq(i).after('<div class="clearfix"></div>');
                            }
                        });
                    })
                } else {
                    pageAlert(data.message, 'danger');
                }
            });
        });
        //图片大图
        box.click(function () {
            $.fancybox(href, {type: 'image'});
            return false;
        });
    }
});
