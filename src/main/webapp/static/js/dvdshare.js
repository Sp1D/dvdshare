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

$(function () {
    $('#btn-add-disk').click(addDiskByForm);
    $('#newdisk-title').keydown(function (event) {
        if (event.which === 13) {
            event.preventDefault();
            addDiskByForm();
        }
    });

    $('.btn-request').click(function () {
        var data = {
            _csrf: csrf.toString(),
            id: $(this).parents('tr').children('.id').html()
        };
        $.post(contextPath + "/rest/request/create/", data, function () {

        });
    });

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
    }
});