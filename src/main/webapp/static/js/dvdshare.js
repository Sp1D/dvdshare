$(function (){
    $('#btn-add-disk').click(function(){
        var data = {
            _csrf : csrf.toString(),
            title : $('#newdisk-title').val()
        };
        
        $.post(contextPath+"/disk/create", data, function (disk){                        
            if (disk !== null) {
                var diskString = '<tr><td class="id">' + disk.id + '</td><td>'
                + disk.title + '</td><td class="holder">' + disk.holder + '</td></tr>';
                
                $('.tbl-mydisks tbody').append(diskString);

            }
        });
        $('#newdisk-title').val("");
        $('#modal-adddisk').modal('hide');
    });
});