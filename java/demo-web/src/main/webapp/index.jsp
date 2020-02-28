<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/captcha.js"></script>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport"/>
</head>
<body>
<a href="https://github.com/jsonljd/captcha" target="_blank">查看github</a>
<br/>
切换交互方式
<br/>
<input type="radio" value="puzzle" name="handlergroup" id="puzzle" checked="checked" /><label for="puzzle">拼图验证</label>
<input type="radio" value="point" name="handlergroup" id="point" /><label for="point">图点击验证</label>
<div  style="position: absolute;left: 300px;top:100px;" id="can"></div>
<script type="application/javascript">
    var conf = {'content':'can'};

    var callback = function(data,postStatus){
        $.ajax({
            url : "<%=request.getContextPath()%>/verifiCode",
            type : "POST",
            dataType: 'text',
            contentType:'application/json;charset=UTF-8',
            data:data,
            success : function(result) {
                if(result=="false") {
                    postStatus(false,function(){
                        init();
                    });

                }else{
                    postStatus(true,function(){
                        alert("success");
                        init();
                    });
                }
            }
        });
    }

    var init = function(){
        $.ajax({
            url : "<%=request.getContextPath()%>/captcha?handler="+$('input:radio:checked').val(),
            success : function(result) {
                fastCaptcha.captcha(conf).build(result,callback);
            }
        });
    }

    $(function(){
        init();
    });

    $('input:radio').change(function(){
        init();
    });
</script>
</body>
</html>
