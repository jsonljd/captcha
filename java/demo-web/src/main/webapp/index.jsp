<html>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="/js/captcha.js"></script>
<body>
<input type="radio" value="puzzle" name="handlergroup" id="puzzle" checked="checked" /><label for="puzzle">puzzle</label>
<input type="radio" value="point" name="handlergroup" id="point" /><label for="point">point</label>
<div id="can"></div>
<script type="application/javascript">
    var conf = {'content':'can'};

    var callback = function(data){
        $.ajax({
            url : "/verifiCode",
            type : "POST",
            dataType: 'text',
            contentType:'application/json;charset=UTF-8',
            data:data,
            success : function(result) {
                if(result=="false") {
                    init();
                }else{
                    alert("success");
                    init();
                }
            }
        });
    }

    var init = function(){
        $.ajax({
            url : "/captcha?handler="+$('input:radio:checked').val(),
            success : function(result) {
                jsonljd.captcha(conf).build(result,callback);
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
