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

    $('.tbl-requests .btn-cancel').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var row = $(this).parents('tr').children('td');
        $.post(contextPath + "/rest/request/delete/", data, function (request) {
            if (request.status === "CANCELLED") {
                row.fadeOut('fast',function(){
                    $(row).remove();
                });
//                $(this).parents('tr').remove();
            }
        });
    });

// Обработка нажатий на кнопки запроса диска и отмены запроса
// на странице другого пользователя

    $('.tbl-mydisks .btn-request').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        var btnreq = $(this);
        var btncancel = $(this).siblings('.btn-cancel-disabled');
        $.post(contextPath + "/rest/request/create/", data, function (response) {
            if (response.status === "REQUESTED") {
                btnreq.addClass('.btn-request-disabled').removeClass('.btn-request');
                btncancel.addClass('.btn-cancel').removeClass('.btn-cancel-disabled');
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
});