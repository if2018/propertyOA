$(function () {
    AccessHouse()

});
//房源获取
function AccessHouse() {
    $.ajax({
        url:"/operating/houseSourceList",
        success:function (data) {
            var dataList=data.data.content;
            new Vue({
                el: '#app',
                data: {
                    items:dataList
                }
            })
        },
        error:function () {
            alert("服务器异常，请刷新重试")
        }
    })
}

function inDetail(id) {
    //alert("详情正在开发中....")
    var first=$(id).children("td").get(0).innerText;
    //alert("id为"+first)
    $.ajax({
        url:"/operating/houseSourceHouseId",
        data:{
            houseId : first
        },
        success: function (data) {
            //alert(data)
            var msg=data.data;

            $("#recipient_detailed_name").attr("value",msg.houseEstate);
            $("#house_detailed_number").attr("value",msg.houseNumber);
            $("#house_detailed_size").attr("value",msg.houseSize);
            $("#house_detailed_price").attr("value",msg.totalPrice);
            $("#landlord_detailed_name").attr("value",msg.landlordName);
            $("#landlord_detailed_phone").attr("value",msg.landlordPhone);
            $("#message_detailed_text").attr("value",msg.houseRemark == null ?  "空" : msg.houseRemark );


            var access=msg.accessRecords;

            $("#access_text").empty();
            //遍历回访记录
            access.forEach(function (msg) {
                 var date=dateFormat(msg.setupTime);
                // console.log(id.record + " " + date)

                $("#access_text").append('<p class="form-control">'
                    + msg.userName
                    + '&nbsp;&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;'
                    + date
                    + '&nbsp;&nbsp;&nbsp;&nbsp;'
                    + msg.record
                    + '</p>')
            })
        },
        error:function () {
            alert("系统异常！！！请稍后重试")
        }
    })
}

//添加房源按钮组,绑定信息的调用
$('#exampleModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // Button that triggered the modal
    var recipient = button.data('whatever'); // Extract info from data-* attributes
    // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    var modal = $(this);
    modal.find('.modal-title').text('房源' + recipient)
    //modal.find('.modal-body input').val(recipient)
});



//添加房源
function submitHouse() {
    //alert("111")
    var houseEstate=$("#recipient_name").val();
    var houseNumber=$("#house_number").val();
    var houseSize=$("#house_size").val();
    var totalPrice=$("#total_price").val();
    var houseRemark=$("#message_text").val();
    var landlordName=$("#landlord_name").val();
    var landlordPhone=$("#landlord_phone").val();
    var accessRecord=$("#access_record").val();
    //var nav_a=$("#nav_a").innerHTML
    //alert(nav_a);

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr) {
        xhr.setRequestHeader(header, token);
    });
    //alert(houseEstate)
    $.ajax({
        type:"post",
        url:"/operating/setupData",
        data:{
            houseEstate : houseEstate,
            houseNumber : houseNumber,
            houseSize : houseSize,
            totalPrice : totalPrice,
            houseRemark : houseRemark,
            landlordName : landlordName,
            landlordPhone : landlordPhone,
            accessRecord : accessRecord

        },
        success:function (data) {
            window.location.href="/index";
            alert(data);

        },
        error:function () {
            alert("添加失败")
        }
    });
    $(".myModal").modal('hide')
}

//根据json中的日期格式，转换成yyyy-mm-dd HH:mm:ss
// function jsonDateFormat(jsonDate) {//json日期格式转换为正常格式
//     try {
//         var date = new Date(parseInt(jsonDate.replace("/Date(", "").replace(")/", ""), 10));
//         var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
//         var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
//         var hours = date.getHours();
//         var minutes = date.getMinutes();
//         var seconds = date.getSeconds();
//         var milliseconds = date.getMilliseconds();
//         return date.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds + "." + milliseconds;
//     } catch (ex) {
//         return "";
//     }
// }
//格式化json传来的时间 2018-08-12T01:37:37.000+0000
function dateFormat(time) {
    var d = new Date(time);

    return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();;
}