/**
 * Created by jecyhw on 16-12-24.
 */

//CREATE_FAIL(400)
//NOT_FOUND(404)
//BINDING_ERROR(401)
//SUCCESS(200)
//FAIL(0)
function addBasePath(suffix) {
    return $(document.body).attr('data-basePath') + suffix;
}

(function (window, $) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    }).ajaxError(function (e, xhr, options) {
        if (xhr.status == 403) {
            confirm.error('会话已超时,请重新登录',function () {
                window.location.href = addBasePath('login');
            });
        }
    });

    var pluploadOptions = {
        runtimes : 'html5,flash,silverlight,html4',
        flash_swf_url : addBasePath('plugin/plupload/Moxie.swf'),
        silverlight_xap_url : addBasePath('plugin/plupload/Moxie.xap'),
        multi_selection: false,
        file_data_name: 'file',
        multipart_params : {
            _csrf: token
        },
        filters : {
            max_file_size : '20mb',
            mime_types: [
                {title : "Image files", extensions : "jpg,gif,png"}
            ]
        }
    };

    var counter = (function () {
        var cnt = -1;
        return {
            increase: function () {
                return new Date().getTime() + '' + (++cnt);
            }
        }
    })();

    var $modal = $('<div>')
        .attr('id', 'modal-template')
        .addClass('hide').load(addBasePath('template/modal.html'), function () {
            $(document.body).append($modal);
        });

    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

    var Pest = function () {
        this.alias = 'pest';
        this.data = {
            0: {
                't': '中文学名',
                'f': 'chineseName'
            },
            1: {
                't': '拉丁学名',
                'f': 'scientificName'
            },
            2: {
                't': '界',
                'f': 'kingdom'
            },
            3: {
                't': '门',
                'f': 'phylum'
            },
            4: {
                't': '亚门',
                'f': 'subPhylum'
            },
            5: {
                't': '纲',
                'f': 'class'
            },
            6: {
                't': '亚纲',
                'f': 'subClass'
            },
            7: {
                't': '目',
                'f': 'order'
            },
            8: {
                't': '亚目',
                'f': 'subOrder'
            },
            9: {
                't': '科',
                'f': 'family'
            },
            10: {
                't': '亚科',
                'f': 'subFamily'
            },
            11: {
                't': '属',
                'f': 'genus'
            },
            12: {
                't': '亚属',
                'f': 'subGenus'
            },
            13: {
                't': '种',
                'f': 'species'
            },
            14: {
                't': '亚种',
                'f': 'subSpecies'
            },
            15: {
                't': '简介',
                'f': 'briefIntroduction'
            },
            16: {
                't': '分布区域',
                'f': 'distributionArea'
            },
            17: {
                't': '形态特征',
                'f': 'morphologicalCharacteristic'
            },
            18: {
                't': '发生规律',
                'f': 'occurrenceRule'
            },
            19: {
                't': '防治方法',
                'f': 'preventionMethod'
            },
            20: {
                't': '生活习性',
                'f': 'livingHabit'
            }
        };
        this.columnsIndex = [1, 7, 9, 11, 13];
    };

    var Disease = function () {
        this.alias = 'disease';
        this.data = {
            0: {
                't': '中文学名',
                'f': 'chineseName'
            }
        };
        this.columnsIndex = [];
    };

    Pest.prototype = Disease.prototype = {
        dt: function () {
            return this.data;
        },
        column: function(code, other) {
            var mp = this.data;
            return $.extend({
                title: mp[code].t,
                data: mp[code].f
            }, other);
        },
        columns: function () {
            var it = this;
            var columns = [
                it.column(0, {
                    'render': function (data, type, row, meta) {
                        return it.row().detailModal(row, data).html();
                    }
                })
            ];
            $.each(it.columnsIndex, function (i, v) {
                columns.push(it.column(v));
            });
            columns.push(
                {
                    title: '操作',
                    data: null,
                    orderable: false,
                    searchable: false,
                    render: function (data, type, row, meta) {//添加 删除、编辑、图片查看按钮
                        var h = btn('danger', '删除', 'remove') + btn('primary', '编辑', 'edit');
                        if (row.pictures && row.pictures.length) {
                            h += btn('primary', '图片', 'picture');
                        }
                        return h;
                    }
                }
            );

            function btn(cls, title, type) {
                return '<button type="button" class="btn btn-xs btn-' + cls
                    + ' ' + type + '" title="' +  title + '"><span class="glyphicon glyphicon-' + type + '"></span></button> ';
            }
            return columns;
        },
        getTByF: function(f) {
            var t = undefined;
            $.each(this.data, function (k, v) {
                if (v.f == f) {
                    t = v.t;
                    return false;
                }
            });
            return t;
        },
        row: function() {
            var it = this;
            return {
                fancyBox: function (pictures, prefixUrl) {
                    prefixUrl = prefixUrl || addBasePath('picture/');
                    if (pictures && pictures.length > 0) {
                        var fancy = [], len = pictures.length;
                        for (var i = 0; i < len; i++) {
                            var picture = {};
                            picture.href = prefixUrl + pictures[i].fileId;
                            picture.title = (pictures[i].title || '') + '(' + (i + 1) + '/' + len + ')';
                            fancy.push(picture);
                        }
                        $.fancybox(fancy, {type: "image", loop: false});
                    }
                },
                detailModal: function (row, colData) {
                    colData = colData || row.chineseName || '';
                    var id = 'detail' + row.id;
                    var $new_modal = $modal.clone();
                    $new_modal.find('a.details-control').attr('title', '查看详情').attr('data-target', '#' + id).html(colData);
                    $new_modal.find('.modal').attr('id', id);
                    $new_modal.find('.modal-title').html(colData);

                    var body = $('<div>').addClass('row'), col3 = [], col12 = [];
                    for (var f in row) {
                        var t = it.getTByF(f);
                        if (t) {
                            var col = $('<div>');
                            col.append('<h4>' + t + '</h4>').append('<p>' + row[f] + '</p>');
                            row[f].length > 30 ? col12.push(col.addClass('col-md-12')) : col3.push(col.addClass('col-xs-6 col-sm-4 col-md-3'));
                        }
                    }
                    $.each(col3.concat(col12), function (i, v) {
                        body.append(v);
                    });
                    $new_modal.find('.modal-body').html(body);
                    return $new_modal.children();
                },
                remove: function (id, callback) {
                    confirm.remove(function () {
                        $.post(addBasePath(it.alias + '/delete/' + id), function () {
                            toastr.success('删除成功');
                            callback();
                        }, 'json');
                    });
                },
                edit: function ($panel, dtRow) {
                    it.infoObj($panel, dtRow);
                },
                create: function ($panel) {
                    $panel.attr('id', counter.increase());//更改id,防止在加载id冲突
                    it.infoObj($panel);
                }
            }
        },
        dataTable: function ($table) {
            var it = this;
            var dt = $table.DataTable({
                'language': {
                    'url': addBasePath("plugin/AdminLTE/plugins/datatables/extensions/i18n/Chinese.json")
                },
                'processing': true,
                'serverSide': true,
                'ajax': {
                    url: addBasePath(it.alias + '/list'),
                    type: 'POST',
                    'dataSrc': function (json) {
                        return json.data || [];
                    }
                },
                rowId: 'id',
                'columns': it.columns(),
                columnDefs: [
                    { targets: '_all', 'defaultContent': ''}
                ]
            });
            $table.on('click', 'button', function () {
                var $t = $(this), itRow = it.row(), dtRow = dt.row($t.closest('tr')), data = dtRow.data();
                if ($t.hasClass('remove')) {
                    itRow.remove(data.id, function () {
                        dtRow.remove().draw();
                    });
                } else if ($t.hasClass('picture')) {
                    itRow.fancyBox(data.pictures);
                } else if ($t.hasClass('edit')) {
                    var m = $modal.find('.modal').clone().appendTo($(document.body)).on('show.bs.modal', function (e) {
                        $(this).find('.modal-body').load(addBasePath(it.alias + '/edit'), function () {
                            it.row().edit(m.find('#create-panel'), dtRow);//由于需要更新表格,所以传递dtRow对象
                        });
                    }).on('hidden.bs.modal', function (e) {
                        m.remove();
                    }).modal('show');
                    m.find('.modal-title').html('编辑');
                }
            });
        }, pv: function ($pv) {//$pv是jq的iframe对象,需要吧iframe的src设置成data-src
            var it = this;
            iframeAutoHeight(it.alias + '-show', $pv);

            $pv.on('load', function () {
                $(this).contents().find('body').on("click", "a.detail-item-link[href]", function () {
                    var href = $(this).attr("href");
                    $.get(addBasePath(href), function (data) {
                        if (data.code == 200) {
                            it.row().fancyBox(data.message);
                        }
                    });
                    return false;
                });
            }).attr('src' , $pv.attr('data-src'));
        },
        infoObj: function ($panel, dtRow) {//带有dtRow表示编辑状态
            var it = this;
            var create = true, exists = false;//create表示是否为创建状态;当create为true,exists才起作用,判断该中文名是否已经存在
            var info = $panel.find('.information');//表单
            var view = $panel.find('.view').hide();//图片表单和视图
            var picObj = new PicObj($panel.find('.upload-picture'), addBasePath(it.alias + '/uploadFile'));//图片功能对象
            if (dtRow) {//编辑状态
                var rowData = dtRow.data();
                picOn(rowData.id);//开启图片上传,绑定记录id
                view.find('button[name="continue"]').parent().remove();//删除继续添加按钮
                view.find('button[name="edit"]').parent().removeClass('col-xs-6').addClass('col-xs-12');
                create = false;
                info.find('.form :input').each(function (i, input) {//一一赋值
                    input.value = rowData[input.name] || '';
                });

                //生成图片列表
                if (rowData.pictures) {
                    $.each(rowData.pictures, function (i, pic) {
                        picObj.appendToList(pic, appendToListCallback(pic, rowData.id));
                    })
                }
            } else {
                view.find('button[name="continue"]').click(function () {//创建页面的继续添加事件
                    toggle();
                    info.find('.form :input').val('');
                    picObj.reset();
                    create = true;
                });

                info.find('input[name="chineseName"]').blur(function () {//创建页面的中文学名输入失去焦点并且文本修改事件,查看是否存在该名字的病虫害数据
                    if (create) {
                        var $it = $(this);
                        var v = $it.val().trim();
                        if (v.length > 0) {
                            $.get(addBasePath(it.alias + '/query?name=' + v), function (data) {
                                var p = $it.parent();
                                if (data.code == 200) {
                                    toastr.error('中文名为"' + v + '"的记录已经存在');
                                    p.addClass('has-error');
                                    exists = true;
                                } else {
                                    exists = false;
                                    p.removeClass('has-error');
                                }
                            });
                        }
                    }
                });
            }

            function toggle(row) {//创建、修改 和 详情之间进行切换
                if (create) {
                    create = false;
                }
                if (row) {
                    info.hide();
                    view.show().children('.row:first').replaceWith(it.row().detailModal(row).find('.row:first'));;
                } else {
                    info.show();
                    view.hide();
                }
            }

            function picOn(id) {//开启图片上传
                picObj.setOptions({
                    BeforeUpload: function (picPanel, up, files) {
                        up.setOption('url', addBasePath(it.alias + '/uploadFile/') + id);
                        up.setOption('params', $.extend(pluploadOptions.multipart_params, {
                            'title': picPanel.find('input[name="title"]').val()
                        }));
                    },
                    FileUploaded: function (picPanel, up, file, picData) {
                        if (picData.code == 200) {
                            var pic = picData.message;
                            picObj.appendToList(pic, appendToListCallback(pic, id)).clear();
                        }
                    }
                }).on();
            }
            function appendToListCallback(pic, id) {//生成图片列表
                return function (col) {
                    $.post(addBasePath(it.alias + '/delete/' + id + '/picture/' + pic.fileId), function (data) {//图片删除事件
                        if (data.code == 200) {
                            toastr.success(data.message);
                            col.remove();
                            //再删除图片文件
                            $.post(addBasePath('picture/delete/' + pic.fileId), function (data) {
                                toastr.success(data.message);
                            })
                        } else {
                            toastr.error(data.message);
                        }
                    });
                };
            }

            info.find('button[name="info"]').click(function () {//添加事件
                if (!exists) {
                    var $it = $(this);
                    $.ajax({
                        url: addBasePath(it.alias + '/create'),
                        type: 'post',
                        dataType: 'json',
                        data: info.find('.form :input').serializeArray(),
                        beforeSend: function () {
                            $it.addClass('disabled');
                            $('.has-error').removeClass('has-error');
                        },
                        success: function (data) {// 返回记录的信息,包括id
                            var entId = data.message.id;
                            if (data.code == 200 && entId) {//添加或者修改基本信息成功
                                if (create) {
                                    toastr.success('添加成功');
                                    info.find('input[name=id]').val(entId);
                                    picOn(entId);
                                } else {
                                    if (dtRow) {
                                        dtRow.data(data).draw();
                                    }
                                    toastr.success('修改成功');
                                }
                                toggle(data.message);
                            } else if (data.code == 401) {//binding error
                                $.each(data.message, function (i, fe) {
                                    info.find('input[name="' + fe.field + '"]').parent().focus().addClass('has-error');
                                    toastr.error(fe.message);
                                });
                            } else {
                                toastr.error(data.message);
                            }
                        },
                        complete: function () {
                            $it.removeClass('disabled');
                        }
                    });
                }
            });

            view.find('button[name="edit"]').click(function () {//基本信息详情显示切到编辑页面
                toggle();
            });
        }
    };

    Pest.prototype.recognition = function (panel) {
        new PicObj(panel, addBasePath('pest/recognition'), {
            FileUploaded: function (pan, up, file, data) {
                if (data.code == 200) {
                    var body = $('<div>').addClass('row');
                    $.each(data.message.pests, function (i, pest) {
                        var pics = pest.pictures;
                        if (pics.length) {
                            var col = $('<div>').addClass('col-xs-6 col-sm-4 col-md-3').appendTo(body);
                            var thumb = Template.createThumb(pics, data.message.pictureUrl + '/', '查看更多图片').appendTo(col)
                            $('<p>').html(Data.pest.row().detailModal(pest)).appendTo(thumb);
                        }
                    });
                    pan.find('.box-body').html(body);
                } else {
                    pan.find('box').addClass('hide');
                }
                pan.find('.overlay').addClass('hide');
            },
            BeforeUpload: function (pan, up, files) {
                pan.find('.result, .overlay').removeClass('hide');
                pan.find('.box-body').html('正在识别中...');
            }
        }).on();
    };

    var PicObj = function (panel, uploadUrl, options) {
        this.isOn = false;
        this.panel = panel;
        this.url = uploadUrl;
        this.options = $.extend({}, options);
        this.disable(true);
    };
    PicObj.prototype = {
        on: function () {
            if (this.isOn) {
                this.disable(false);
            } else {
                var pan = this.panel, o = this.options, it = this;
                var fileDiv = pan.find('.filename');
                var uploader = new plupload.Uploader($.extend(pluploadOptions, {
                    container: pan.get(0),
                    browse_button: pan.find('button[name="file"]').get(0),
                    url: this.url,
                    init: {
                        PostInit: function () {
                            fileDiv.html('');
                            it.disable(false);
                            pan.find('button[name="picture"]').click(function () {
                                uploader.start();
                            });
                            !o.PostInit || o.PostInit(pan);
                        },
                        FilesAdded: function (up, files) {
                            //保证每次只上传一个文件
                            var newFile = files[0];
                            plupload.each(up.files, function (file) {
                                if (file.id != newFile.id) {
                                    up.removeFile(file);
                                }
                            });
                            fileDiv.attr('id', newFile.id).html('<div class="alert alert-info">' + newFile.name + ' (' + plupload.formatSize(newFile.size) + ')</div>');
                            it.previewPic(pan.find('.preview'), newFile);
                            pan.find('.progress-bar').css('width', 0);
                            !o.FilesAdded || o.FilesAdded(pan, up, files);
                        },

                        BeforeUpload: function (up, files) {
                            it.disable(true);
                            !o.BeforeUpload || o.BeforeUpload(pan, up, files);
                        },
                        UploadProgress: function (up, file) {
                            var percent = file.percent + '%';
                            pan.find('.progress-bar').css('width', percent).html(percent)
                            !o.BeforeUpload || o.BeforeUpload(pan, up, file);
                        },

                        Error: function (up, err) {
                            it.disable(false, true);
                            toastr.error('上传错误 #' + err.code + ': ' + err.message);
                            !o.Error || Error(pan, up, err);
                        },

                        FileUploaded: function (up, file, result) {
                            var data = eval('(' + result.response + ')');
                            if (data.code != 200) {
                                toastr.error(data.message);
                            }
                            it.disable(false);
                            !o.FileUploaded || o.FileUploaded(pan, up, file, data);
                        }
                    }
                }));
                fileDiv.html('<div class="alert alert-danger">您的浏览器不支持 Flash, Silverlight 和 HTML5.</div>');
                uploader.init();
                this.isOn = true;
            }
            return this;
        },
        previewPic: function($container, file) {//图片预览
            var thumb = $('<div>').appendTo($container.html(''));
            var img = new moxie.image.Image();
            img.onload = function () {
                thumb.attr('id', this.uid).addClass('thumbnail').html('');
                this.bind('embedded', function () {
                    thumb.children().css({
                        width: '100%',
                        height: 'auto'
                    });
                });
                this.embed(this.uid);
            };
            img.onerror = function () {
                $container.html('图片预览失败');
            };
            img.load(file.getSource());
            return this;
        },
        reset: function () {
            this.disable(true, true);
            this.panel.find('.list').html('');
            return this;
        },
        disable: function (status, clear) {
            var pan = this.panel;
            var input = pan.find(':input').prop('disabled', status);
            if (clear) {
                this.clear();
            }
            return this;
        },
        clear: function () {
            var pan = this.panel;
            pan.find(':input').val('');
            pan.find('.progress-bar').css('width', 0);
            pan.find('.preview, .filename').html('');
            return this;
        },
        setOptions: function (options) {
            this.options = $.extend(this.options, options);
            return this;
        },
        appendToList: function (pic, callback) {
            var col = $('<div>').addClass('col-xs-6 col-sm-4').appendTo(this.panel.find('.list'));
            var thumb = Template.createThumb([pic], addBasePath('picture/'), '查看原图').appendTo(col);
            var caption = $('<div>').addClass('caption').appendTo(thumb);
            if (pic.title) {
                caption.append('<strong>' + pic.title + '   </strong>');
            }
            $('<a>').addClass('btn btn-xs btn-danger').html($('<span>').addClass('glyphicon glyphicon-remove'))
                .append(' 删除').click(function () {
                confirm.remove(function () {
                    callback(col);
                });
            }).appendTo(caption);
            return this;
        }
    };

    var Template = {
        createThumb: function (pics, url, title) {
            var thumb = $('<div>').addClass('thumbnail');
            var a = $('<a>').attr('href', '#').attr('title', title || '')
                .click(function () {
                    Data.pest.row().fancyBox(pics, url);
                }).appendTo(thumb);
            $('<img>').attr('src', url + pics[0].fileId).css('height', '200px').appendTo(a);
            return thumb;
        }
    };

    var confirm = (function () {
        var defaultO = {
            animation: 'top',
            closeAnimation: 'top',
            closeIcon: true,
            closeIconClass: 'fa fa-close',
            buttons: {
                ok: {
                    text: '确定'
                }
            }
        };
        return {
            remove: function (okAction) {
                $.confirm($.extend(defaultO, {
                    icon: 'fa fa-warning',
                    title: '警告',
                    type: 'red',
                    content: '你确定要删除吗',
                    backgroundDismiss: true,
                    buttons: {
                        ok: {
                            btnClass: 'btn-danger',
                            action: okAction
                        },
                        cancel: {
                            text: '取消'
                        }
                    }
                }));
            },
            error: function (content, okAction) {
                $.confirm($.extend(defaultO, {
                    icon: 'fa fa-warning',
                    title: '错误',
                    type: 'red',
                    content: content,
                    buttons: {
                        ok: {
                            btnClass: 'btn-danger',
                            action: okAction
                        }
                    }
                }))
            }
        }
    })();

    window.iframeAutoHeight = function(id, $container) {
         $container.parents('.box').css('margin', 0);
         var dif = $('.main-header').outerHeight() + $('.main-footer').outerHeight()
             + $('.content').outerHeight(true) - $('#' + id + ' .box-body').height() - $('.nav.nav-tabs').outerHeight() + 5;

         $(window).resize(function () {
             if (!$container.hasClass('fullscreen')) {
                 $container.height($(window).height() - $('.nav.nav-tabs').outerHeight() - dif);
             } else {
                 $container.height('100%');
             }

         }).resize();
    }

    window.Data = {
        pest : new Pest(),
        disease: new Disease()
    };
})(window, jQuery);
