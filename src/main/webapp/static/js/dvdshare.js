function addDiskByForm() {
    var data = {
        _csrf: csrf.toString(),
        title: $('#newdisk-title').val()
    };

    $.post(contextPath + "/disk/create", data, function (disk) {
        if (disk !== null) {
            var diskString = '<tr><td class="id">' + disk.id + '</td>'
                    + '<td>' + disk.title + '</td>'
                    + '<td class="owner">' + disk.owner.username + '</td>'
                    + '<td class="holder">' + disk.holder.username + '</td></tr>';

            $('.tbl-mydisks tbody').append(diskString);

        }
    });
    $('#newdisk-title').val("");
    $('#modal-adddisk').modal('hide');
}



function cancelRequest(obj) {
    var data = {
        _csrf: csrf.toString(),
        id: $(obj).parents('tr').children('.id').html()
    };
    $.post(contextPath + "/rest/request/delete/", data, function (request) {
        if (request.status === 'CANCELLED') {

        }
    });
}

$(function () {
    $('#btn-add-disk').click(addDiskByForm);
    $('#newdisk-title').keydown(function (event) {
        if (event.which === 13) {
            event.preventDefault();
            addDiskByForm();
        }
    });

// Обработка нажатий на кнопки отмены запроса диска на своей странице

    $('.tbl-requests #btn-cancel').click(function () {
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

    $('.tbl-requests #btn-reject').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var btnreject = $(this);
        var btnaccept = $(this).siblings('#btn-accept');
        var tdstatus = $(this).parents('tr').children('.status');
        $.post(contextPath + "/rest/request/reject/", data, function (response) {
            if (response.status === "REJECTED") {                
                var icon = '<span class="glyphicon glyphicon-remove-sign" style="color:red;"></span>';
                tdstatus.html(icon);
            }
        });
    });

    $('.tbl-requests #btn-accept').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var btnreject = $(this);
        var btnaccept = $(this).siblings('#btn-accept');
        var tdstatus = $(this).parents('tr').children('.status');
        $.post(contextPath + "/rest/request/accept/", data, function (response) {
            if (response.status === "ACCEPTED") {
                var icon = '<span class="glyphicon glyphicon-ok-sign" style="color:green;"></span>';
                tdstatus.html(icon);
            }
        });
    });

// Обработка нажатий на кнопки запроса диска и отмены запроса
// на странице другого пользователя

    $('.tbl-mydisks #btn-request').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var btnreq = $(this);
        var btncancel = $(this).siblings('#btn-cancel');
        $.post(contextPath + "/rest/request/create/", data, function (response) {
            if (response.status === "REQUESTED") {
                btnreq.addClass('btn-disabled');
                btncancel.removeClass('btn-disabled');
            }
        });
    });


//  Установка активной вкладки

    $('#tabs li').removeClass('active');
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

    $('table.tbl-requests td.status').each(function(){
        
        switch ($(this).html()) {
            case "REJECTED" :                
                $(this).siblings('td.request').children('#btn-accept').addClass('btn-disabled');
                $(this).siblings('td.request').children('#btn-reject').removeClass('btn-disabled');
                break;
            case "ACCEPTED" :                 
                $(this).append('&nbsp<span class="glyphicon glyphicon-ok-sign" style="color:green;"></span>');
                $(this).siblings('td.request').children('#btn-accept').removeClass('btn-disabled');
                $(this).siblings('td.request').children('#btn-reject').addClass('btn-disabled');
                break;
            case "REQUESTED" :
                $(this).siblings('td.request').children('#btn-accept').removeClass('btn-disabled');
                $(this).siblings('td.request').children('#btn-reject').removeClass('btn-disabled');
                break;
        }
    });




});