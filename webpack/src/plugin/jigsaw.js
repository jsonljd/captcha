function jigsaw(){
			var name = "PUZZLE_HANDLER";
			var build = function(conf,callbak){
				var canvas = conf.unit.createCanvas(conf.bgWidth,conf.bgHight);
				var ctx = canvas.getContext('2d');
				var originX, originY;
				originX = conf.captchaData.params.i_x;
				originY = conf.captchaData.params.i_y;
				var xqo = conf.captchaData.data;
				var img = new Image();			
				var img_sec = new Image();
				img.setAttribute('crossOrigin', 'anonymous');
				img_sec.setAttribute('crossOrigin', 'anonymous');
				for(var i in xqo){
					if(xqo[i].type==="PRIMARY_IMG"){
						img.src = "data:image/png;base64,"+ xqo[i].img;
						img.onload = function(){				
							ctx.drawImage(img,0,0);
							conf.unit.imgdecode(ctx,conf);
						}					
					}

					if(xqo[i].type==="SECONDARY_IMG"){
						img_sec.src = "data:image/png;base64,"+ xqo[i].img;
						img_sec.onload = function(){	
							ctx.drawImage(img_sec,originX,originY,conf.jigsawWidth,conf.jigsawHight);
						}
					}
				}
				conf.parContent.appendChild(canvas);
				canvas.onmousedown = function(e){						
						var finalX;
						canvas.onmousemove = function(e){
							finalX = e.clientX;
							chkX();
							ctx.clearRect(0,0,canvas.width,canvas.height);
							ctx.drawImage(img,0,0);
							conf.unit.imgdecode(ctx,conf);
							ctx.drawImage(img_sec,finalX,originY,conf.jigsawWidth,conf.jigsawHight);
						};

						var chkX = function(){
							if (finalX >= (canvas.width - conf.jigsawWidth))
							{
								finalX = canvas.width - conf.jigsawWidth;
							}
							if(finalX<originX){
								finalX = originX;
							}

						}

						var init = function(){
							if(finalX)
							{
								var timer = null;
								var firstCurr = finalX;
								var tempCurr = finalX;
								clearInterval(timer);
								timer = setInterval(function () {
									var iSpeed = (originX - tempCurr) / 6;
									iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
									tempCurr = tempCurr + iSpeed;
									finalX = tempCurr;
									if (originX == tempCurr) {
										clearInterval(timer);
									} else {
										ctx.clearRect(0,0,canvas.width,canvas.height);
										ctx.drawImage(img,0,0);
										conf.unit.imgdecode(ctx,conf);
										ctx.drawImage(img_sec,tempCurr,originY,conf.jigsawWidth,conf.jigsawHight);
									}
									
								}, 30);
							}else{
								ctx.clearRect(0,0,canvas.width,canvas.height);
								ctx.drawImage(img,0,0);
								conf.unit.imgdecode(ctx,conf);
								ctx.drawImage(img_sec,originX,originY,conf.jigsawWidth,conf.jigsawHight);
							}
							
							canvas.onmousemove = null;
							canvas.onmouseup = null;
							canvas.onmouseout = null;
							
						};

						canvas.onmouseup = function(){						
							init();
							if(finalX){								
								callbak(JSON.stringify(finalX));
							}
						};

						canvas.onmouseout = function(){						
							init();
						};
				}
			};
			return {name:name,build:build};
};

module.exports = jigsaw();
