function addDiskByForm() {
    var data = {
        _csrf: csrf.toString(),
        title: $('#newdisk-title').val()
    };

    $.post(contextPath + "/disk/create", data, function (disk) {
        if (disk !== null) {
            var diskString = '<tr><td class="id">' + disk.id + '</td>'
                    + '<td>' + disk.title + '</td>'
                    + '<td class="return"></td>'
                    + '<td class="owner">' + disk.owner.username + '</td>'
                    + '<td class="holder">' + disk.holder.username + '</td></tr>';

            $('.tbl-mydisks tbody').append(diskString);

        }
    });
    $('#newdisk-title').val("");
    $('#modal-adddisk').modal('hide');
}



$(function () {
    /*
     * Кнопка добавления нового диска
     */
    $('#btn-add-disk').click(addDiskByForm);
    
    /*
     * Добавление диска по нажатию ENTER в форме
     */
    $('#newdisk-title').keydown(function (event) {
        if (event.which === 13) {
            event.preventDefault();
            addDiskByForm();
        }
    });

/*
 * Кнопка возврата диска владельцу
 */
    $('.tbl-mydisks a.btn-return').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var row = $(this).parents('tr').children('td');
        $.post(contextPath + "/rest/take/back", data, function (response) {
            if (response.holder.id === response.owner.id) {
                row.fadeOut('fast', function () {
                    $(row).remove();
                });
            }
        });
    });

/*
 * Кнопка отмены (удаления) запроса со страницы запросов
 */

    $('.tbl-requests span.btn-cancel').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var row = $(this).parents('tr').children('td');
        $.post(contextPath + "/rest/request/delete/", data, function (response) {
            if (response.status === "CANCELLED") {
                row.fadeOut('fast', function () {
                    $(row).remove();
                });
            }
        });
    });

/*
 * Кнопка отказа для запроса (установка статуса REJECTED)
 */
    $('.tbl-requests span.btn-reject').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var btnreject = $(this);
        var btnaccept = $(this).siblings('btn-accept');
        var tdstatus = $(this).parents('tr').children('.status');
        $.post(contextPath + "/rest/request/reject/", data, function (response) {
            if (response.status === "REJECTED") {
                var icon = '<span class="glyphicon glyphicon-remove-sign" style="color:red;"></span>';
                tdstatus.html(icon);
            }
        });
    });

/*
 * Кнопка подтверждения запроса (установка статуса ACCEPTED)
 */
    $('.tbl-requests span.btn-accept').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var btnreject = $(this);
        var btnaccept = $(this).siblings('btn-accept');
        var tdstatus = $(this).parents('tr').children('.status');
        $.post(contextPath + "/rest/request/accept/", data, function (response) {
            if (response.status === "ACCEPTED") {
                var icon = '<span class="glyphicon glyphicon-ok-sign" style="color:green;"></span>';
                tdstatus.html(icon);
            }
        });
    });

/*
 * Кнопка взятия диска из подтвержденного запроса на странице запросов
 */
    $('.tbl-requests a.btn-take').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var row = $(this).parents('tr').children('td');
        var title = $(this).parents('tr').children('.title').html();
        $.post(contextPath + "/rest/take/", data, function (response) {
            if (response.disk.title === title) {
                row.fadeOut('fast', function () {
                    $(row).remove();
                });
            }
        });
    });

/*
 * Кнопка запроса диска на странице пользователя или странице всех дисков
 */
    $('.tbl-mydisks span.btn-request').click(function () {
        if ($(this).hasClass('btn-disabled'))
            return;
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var btnreq = $(this);
        var btncancel = $(this).siblings('.btn-cancel');
        $.post(contextPath + "/rest/request/create/", data, function (response) {
            if (response.status === "REQUESTED") {
                btnreq.addClass('btn-disabled');
                btncancel.removeClass('btn-disabled');
            }
        });
    });

/*
 * Кнопка отмены запроса на странице пользователя или странице всех дисков
 */
    $('.tbl-mydisks span.btn-cancel').click(function () {
        if ($(this).hasClass('btn-disabled'))
            return;
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var btncancel = $(this);
        var btnreq = $(this).siblings('.btn-request');
        $.post(contextPath + "/rest/request/delete/bydisk", data, function (response) {
            if (response.status === "CANCELLED") {
                btnreq.removeClass('btn-disabled');
                btncancel.addClass('btn-disabled');
            }
        });
    });

/*  Установка активной вкладки
 *  Серверной стороной устанавливается значение выборки, которую
 *  мы показываем для текущей страницы. Выборки обычно показаны на 
 *  разных вкладках одной .jsp
 */
    var dataSelection = ds;

    switch (dataSelection) {
        case "OWN" :
            $('#tab-own').toggleClass('active');
            break;
        case "HOLD" :
            $('#tab-hold').toggleClass('active');
            break;
        case "TAKEN" :
            $('#tab-taken').toggleClass('active');
            break;
        case "GIVEN" :
            $('#tab-given').toggleClass('active');
            break;
        case "IN" :
            $('#tab-in').toggleClass('active');
            break;
        case "OUT" :
            $('#tab-out').toggleClass('active');
            break;
    }


});