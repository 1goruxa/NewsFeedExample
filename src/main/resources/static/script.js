$(document).ready ( function(){
    $.ajax
           ({
                method: "GET",
                url: '/groups',
                contentType: "application/json; charset=utf-8",
                success: function(response) {
                    var sel = document.getElementById('group_list');
                    var selEdit = document.getElementById('editGroup_list');
                    for(i in response) {
                           var opt = document.createElement('option');
                           var opt2 = document.createElement('option');
                           opt.appendChild(document.createTextNode(response[i].name));
                           opt2.appendChild(document.createTextNode(response[i].name));
                           opt.value = response[i].id;
                           opt2.value = response[i].id;
                           sel.appendChild(opt);
                           selEdit.appendChild(opt2);
                    }

                }
            });

            loadPosts("all");
            loadGroups();

});


$(function(){
            $("#search").click(function() {
                var filter = document.getElementById("search_text").value;
                loadPosts(filter);
            });
        });

$(function(){
            $("#new").click(function() {
                openForm()
            });
        });

$(function(){
            $("#edit").click(function() {
                var selected_news = $('input[name=news_select]:checked').val();
                if (selected_news == undefined ) {alert("Нужно выбрать новость");}
                    else{
                        //Получаем новость на редактирование
                        $.ajax
                            ({
                                method: "GET",
                                url: "/getsinglenews",
                                data: {
                                    newsId : selected_news
                                },
                                contentType: "application/json; charset=utf-8",
                                success: function(response) {
                                            openEditForm(response)
                                       }
                                 });
                    }
            });
        });


$(function(){
            $("#delete").click(function() {
                var selected_news = $('input[name=news_select]:checked').val();
                                if (selected_news == undefined ) {alert("Нужно выбрать новость");}
                                    else{
                                        var textForm = {"id":selected_news};
                                        $.ajax
                                            ({
                                                method: "POST",
                                                url: "/deletenews",
                                                data: JSON.stringify(textForm),
                                                contentType: "application/json; charset=utf-8",
                                                success: function(response) {
                                                            if (response.result=true) {alert("Новость удалена");}
                                                                else{
                                                                    alert(response.error)
                                                                }
                                                            loadPosts("all");
                                                       }
                                                 });
                                    }
            });
        });


function openForm() {
    document.getElementById("myForm").style.display = "block";
}

function openEditForm(response) {
    document.getElementById("editForm").style.display = "block";
    document.getElementById("idNews").innerHTML =  response.id;
    document.getElementById("editName_1").value = response.name;
    document.getElementById("editText_1").value = response.text;
}

function sendEditedAndcloseForm(){
var id = document.getElementById("idNews").textContent;

var textForm = {"id": id, "name":$("#editName_1").val(),"text":$("#editText_1").val(),"group":$("#editGroup_list").val()};

 $.ajax
        ({
            method: "POST",
            url: "/updatenews",
            data: JSON.stringify(textForm),
            contentType: "application/json; charset=utf-8",
            success: function(response) {
                        if (response.result==true) {alert("Новость обновлена!");}
                            else{
                                alert(response.error)
                            }
                        loadPosts("all");
                   }
             });

    document.getElementById("editForm").style.display = "none";

}

function sendAndcloseForm() {

var textForm = {"name":$("#name_1").val(),"text":$("#text_1").val(),"group":$("#group_list").val()};

 $.ajax
        ({
            method: "POST",
            url: "/addnews",
            data: JSON.stringify(textForm),
            contentType: "application/json; charset=utf-8",
            success: function(response) {
                        if (response.result==true) {alert(response.result);}
                            else{
                                alert(response.error)
                            }
                        loadPosts("all");
                   }
             });

    document.getElementById("myForm").style.display = "none";

}

function justCloseForm() {
   document.getElementById("editForm").style.display = "none";
}

function loadPosts(filter){
    $(function () {

        data = {"filter" : filter};
        $.ajax
           ({
                method: "GET",
                url: '/news',
                data: data,
                contentType: "application/json; charset=utf-8",
                success: function(response) {
                        var listNews = "";
                        for (i in response){
                            listNews = listNews + "<input type=\"radio\" name=\"news_select\" value=\"" + response[i].id + "\">" +response[i].date + "(" +response[i].group + ")<h3>" + response[i].name + ":</h3>" +
                                       response[i].text + "<br> <br>";
                        }
                        document.getElementById('news_position').innerHTML = listNews
                    }
                });
            });
    }

function loadGroups(){
    $(function () {
            $.ajax
               ({
                    method: "GET",
                    url: '/groups',
                    contentType: "application/json; charset=utf-8",
                    success: function(response) {
                            var listGroups = "";
                            for (i in response){
                                var nameGroup = response[i].name;
                                var funcGroup = "loadPosts(\'" +nameGroup + "\')";
                                listGroups = listGroups + "<a href=" + "\"javascript:void(0)\"" + " onclick=\"" + funcGroup +"\">"
                                        + nameGroup + "</a><br>";
                            }
                            listGroups = listGroups + "<a href=" + "\"javascript:void(0)\"" + " onclick=\"loadPosts(\'all\')\">"
                                                                    +  "ВСЕ НОВОСТИ</a><br>";
                            document.getElementById('group_position').innerHTML = listGroups;
                        }
                    });
                });

}


