var captcha = function(){
    var auhor = 'liangjiandong',version = 'v1.0.0',lastTime = '2020.2.28';
		var unit = (function(){
			var win = window;
			var createElement = function(tarname){
				var win = window;
				return win.document.createElement(tarname);
			};

			var createCanvas = function(width, height) {
						var canvas = createElement("canvas");
						canvas.width = width;
						canvas.height = height;
						return canvas;
			}

			 var getId = function getId(id) {
					return win.document.getElementById(id);
			 }

			 var extend = function() {
				var length = arguments.length;
				var target = arguments[0] || {};
				if (typeof target!="object" && typeof target != "function") {
					target = {};
				}
				if (length == 1) {
					target = this;
					i--;
				}
				for (var i = 1; i < length; i++) { 
					var source = arguments[i]; 
					for (var key in source) {
						if (Object.prototype.hasOwnProperty.call(source, key)) { 
							target[key] = source[key]; 
						} 
					} 
				}
				return target; 
			}

			var T8T = {};
            T8T.F8N = function(){
                return typeof T8T.V8N.m9p === 'function' ? T8T.V8N.m9p.apply(T8T.V8N, arguments) : T8T.V8N.m9p;
            };

            T8T.AB8 = function(){
                return typeof T8T.YU0.atx === 'function' ? T8T.YU0.atx.apply(T8T.YU0, arguments) : T8T.YU0.atx;
            }

            T8T.YU0 = function(){
                return {
                    atx:function(yc){
                        var tt = 37;
                        var mt = ",";
                        while( tt !== 92){
                            switch (tt) {
                                case 37:
                                    st = 0;
                                    tt = 39;
                                    tx = yc.split(mt);
                                    break;
                                case 39:
                                    xb = "";
                                    tt = 29;
                                    break;
                                case 29:
                                    tt = st < tx.length ? 46: 70;
                                    break;
                                case 46:
                                    xb += String.fromCharCode(tx[st++]);
                                    tt = 29;
                                    break;
                                case 70:
                                    xb = xb.split(mt);
                                    return function (at) {
                                        return xb[at];
                                    }
                                    break;
                            }
                        }
                    }("102,117,110,99,116,105,111,110,44,100,101,99,111,100,101,85,82,73,44,108,101,110,103,116,104,44,102,114,111,109,67,104,97,114,67,111,100,101,44,99,104,97,114,67,111,100,101,65,116,44,83,116,114,105,110,103,44,74,98,56,66,120,38,44,115,112,108,105,116,44,96")
                }
            } ();

            T8T.V8N = function () {
                return {
                    m9p:function(w3d) {
                        var S3d = 2;
                        while (S3d !== 14) {
                            switch (S3d) {
                                case 2:
                                    var F1f = '',
                                        W1f = win[T8T.AB8(1)]("-%07L%0B%15G-%07%7C#%0CG*%12M1%10F:%17L%0B%15G-%07%7C#%0CG*%06W!%0DK/%0CL%22%0BR3%0E%5D%22%1BT/%03L'=J/%0F%5D,%0CF0+V&%1D%5E*%12W1%11R#%0DV%22%15G8%05Q,%18J/%04L%22%0CI:%02O+%1CR%22%02P'%11A%22%16X!%19H%3C%03K%22%17H&%0DY&%18E8%0DK17T#%05Q,%18U8%01X6%01V/%02S'%01F.%03L#%18V+%10%5D,%0Cy?%10T%22%0BC$%01g7%0AJ*%05%5D6;I$%16%5D:%0CF.%10Y51K+%05%5D%22%19V:%07V&;N#%0E%5C%22%1FC%3E'T'%15C$%16z;1B*%0BU%25%18g$%0DV;%15I?%11Xp%1CF'%0DM1%1DF(T%0C%222u%05,X1%0CT#%0C_+%1E_*%01Y.%14F%E9%AB%86%E8%AE%A3%E5%A4%89%E8%B5%A7%EF%BD%B4%E8%AF%91%E6%8D%83%E6%8E%B2%E7%A4%82%E6%92%8F%E4%BC%A4F/%10J-%0AF'%0DM1%1DK%25%14%5D%22%15I?%11%5D7%08F+%06%5C%07%0EC$%16t+%0BR/%0C%5D0%18E&%07Y0*C)%16X%1D%0EC8%0B%5E1'V+%10Y/%18O%15%1BX%20%1Fy=%0B%5C6%10F(%05g*%1DO-%0AL%22%14C$%05L*%18O%15%03J0%18O%15%01g1%18T/%0EY6%11P/:X0%1DJ+%16Q4%1D%7F*%0Bg1%18L#%05K#%0Fy(%16V%1D%0FO.%16P%22%12O-%11Y5'D%3E%0Cg*%1DO-%0AL%22%0CG8%05%5D6%18%5E*%1BX!%19H%3C%03K%06%11P*%01Y,%0EG9&Q4;J!%02%5B0%1DG%3E%07X%7D%15C%3E%0AW&EFl%10Y,EFl%09%5D;EF)%0ES+%15A*%14Q'%0FO'%05X%25%1DR*%16P'%16F%07%03L*%18T+%0C%5C-%15F%15%14%5D0%11@#=%5C-%0FH*%0FW7%0BC*%1AH#%0CN*%11%5D0%0EO)%07X&%1DP'%05U6WJ%25%05Q,WG?%16W!%17B/%02%5C'%0EK-%0FLm%14I-%0BVm%19S%3E%0D%5B-%1CC)%0A%5D!%13F:%0DK6%18U:%0EQ6%18%0A*%E8%AE%95%E5%9C%90%E4%B9%89%E5%9A%86%E4%BE%BB%E6%AD%AB%E7%83%9B%E5%87%83%22%15I.%03Tm%0EG&%0B%5C#%0CC%09%0EQ!%13%08%3E%12Tl%10R'%0EX/%17B+%0E%174%19J#%06Y6%1Du&%0B%5C'VR:%0E%16*%0CK&%02%0BrHF%7BQ%08%22M%13*RX1%0CG%3E%17K%22%18N+%11w5%16v8%0DH'%0AR3%02%E6%9C%B5%E5%8B%A3%E7%B8%B9%E5%BF%BF%EF%BD%86%E8%AE%95%E7%A8%B5%E5%91%8C%E9%86%B5%E8%AF%B3*%17V&%1D@#%0C%5D&%18O%15%1AX!%14G9%11v#%15C*%0BV,%1DT%026u%0E%18E&%0BU#%0AM*%11H#%16F:%1AXa%1BI$%16%5D,%0CF/%0E%5D/%1DH%3E%02Y,%1FS&%03J%22%19V:%07V&%18N+%11%7B.%19U9%02%5B1%0BF+%00K-%14S%3E%07Xt%08%5E*%0DH#%1BO%3E%1BX6%0AG$%11%5E-%0AK*L_'%1DR/%11L%1D%0BJ#%06%5D0'R#%12X6%0AG$%11T#%0CCb%02H:T%06z%12@k%18N#%06Xs%18%16*%E5%89%95%E6%96%88%22%5BE%25%0CL'%16R%15%0BL'%15F(=U%1D%11");
                                    S3d = 1;
                                    break;
                                case 1:
                                    var X1f = 0,
                                        B1f = 0;
                                    S3d = 5;
                                    break;
                                case 5:
                                    S3d = X1f < W1f[T8T.AB8(2)] ? 4 : 7;
                                    break;
                                case 4:
                                    S3d = B1f === w3d[T8T.AB8(2)] ? 3 : 9;
                                    break;
                                case 3:
                                    B1f = 0;
                                    S3d = 9;
                                    break;
                                case 8:
                                    X1f++,
                                        B1f++;
                                    S3d = 5;
                                    break;
                                case 9:
                                    F1f += win[T8T.AB8(5)][T8T.AB8(3)](W1f[T8T.AB8(4)](X1f) ^ w3d[T8T.AB8(4)](B1f));
                                    S3d = 8;
                                    break;
                                case 7:
                                    F1f = F1f[T8T.AB8(7)](T8T.AB8(8));
                                    return function(l3d) {
                                        var V3d = 2;
                                        while (V3d !== 1) {
                                            switch (V3d) {
                                                case 2:
                                                    return F1f[l3d];
                                                    break;
                                            }
                                        }
                                    };
                                    break;
                            }
                        }
                    } (T8T.AB8(6))
                }
            } ();

			var _init_dat = function(dat){
                var ii = 2;
                while(ii!=99) {
                    switch (ii) {
                        case 2:
                            len = dat[0] & 0xFF;
                            ii = 4;
                            break;
                        case 4:
                            ind = 0;
                            i = 0;
                            ii = 10;
                            aa = "";
                            break;
                        case 10:
                            ii = i < dat[T8T.F8N(44)]?9:1;
                            break;
                        case 9:
                            item = dat[i];
                            dex = 0;
                            do {
                                if (len >= ind) {
                                    if (ind != 0) {
                                        aa += (item >> (dex * 8) & 0xFF)+T8T.F8N(75);
                                    }
                                    ind++;
                                }
                                dex++;
                            } while (dex < 4);
                            i++;
                            ii = 10;
                            break;
                        case 1:
							var mm = aa[T8T.F8N(74)](T8T.F8N(75));
						    var tt = new Map();			
							for(var i=T8T.F8N(82);i<mm[T8T.F8N(44)];i++){	
								tt.set(mm[i].toString(),i);
							}
                            return tt;
                            break;
                    }
                }
            }

			var _getInd = function(arr,ii){
                for (var i = 0; i < arr[T8T.F8N(44)]; i++) {
                    if(arr[i]==ii){
                        return i;
                    }
                }
                return -1;
            };

			var _img_make = function(ctx,conf){
                var dat = _init_dat(conf.captchaData.params[T8T.F8N(45)]);
                var wegiht = conf.captchaData.params[T8T.F8N(46)];
                var hh = conf.bgHight >> 1;
                var h = conf.bgHight;
                var ImageDataList = [];
                var ImageData;
				for(var i=T8T.F8N(82);i<dat.size-1;i++){	
					ImageData = ctx[T8T.F8N(0)](dat.get(i.toString())*wegiht, T8T.F8N(82), wegiht, h);
                    ImageDataList[T8T.F8N(1)](ImageData);
                }
				for(var i=T8T.F8N(82);i<dat.size-1;i++){
                    ctx[T8T.F8N(2)](ImageDataList[i],wegiht*i,T8T.F8N(82));
                }

                ImageDataList = [];
                ImageDataList[0] = ctx[T8T.F8N(0)](T8T.F8N(82), T8T.F8N(82), conf.bgWidth, hh);
                ImageDataList[1] = ctx[T8T.F8N(0)](T8T.F8N(82), hh, conf.bgWidth, hh);

                ctx[T8T.F8N(2)](ImageDataList[0],T8T.F8N(82),hh);
                ctx[T8T.F8N(2)](ImageDataList[1],T8T.F8N(82),T8T.F8N(82));
                return ctx[T8T.F8N(0)](T8T.F8N(82),T8T.F8N(82),conf.bgWidth, h);
            }

			return {
				'extend':extend,
				'createElement':createElement,
				'createCanvas':createCanvas,
				'getId':getId,
                'imgdecode':_img_make

			};
		})();
		

		var buildConfig = {'content':'','bgWidth': 300,'bgHight':130,'jigsawWidth':45,'jigsawHight':45,'tipHeight':28};	
		var pluginMap = new Map();

		var captcha = function(conf){
			conf = unit.extend(buildConfig,conf);
			var content = unit.getId(conf.content);

			
			var build = function(captchaData,callback){	
				content.innerHTML = '';
				var xqo = eval('(' + captchaData + ')');
				if(xqo && xqo.factory){					
					var plugTmp = pluginMap.get(xqo.factory);
					if(plugTmp){
						var confTmp = unit.extend(conf,xqo.params);
						var allConf = {};
						allConf["captchaData"] = xqo;
						allConf["parContent"] = content;
						allConf["unit"] = unit;
						allConf = unit.extend(confTmp,allConf);
						plugTmp.build(allConf,callback);
					}
				}				
			}

			return {
				'build':build
			};
		}

		var plugin = function(pluginImpi){
			pluginMap.set(pluginImpi.name,pluginImpi);
			return pluginMap;
		}	

        var help = function() {
            var url = "https://github.com/jsonljd/captcha";
            window.open(url, '_brank', 'height=600,width=480,top=0,left=0,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes');
            return true;
        };

        return {
            'version': version,
            'captcha':captcha,            
            'plugin':plugin,
            'help': help
        };
};

var exp = captcha();

exp.plugin(require('./plugin/jigsaw'));

exp.plugin(require('./plugin/point-click'));

module.exports = exp;