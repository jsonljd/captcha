function jigsaw(){
			var name = "PUZZLE_HANDLER";
			var build = function(conf,callbak){
				var canvas = conf.unit.createCanvas(conf.bgWidth,conf.bgHight);
				var ctx = canvas.getContext('2d');

				var tipHeight = conf.tipHeight;

				var canvas2 = conf.unit.createCanvas(conf.bgWidth,tipHeight);
				var ctx2 = canvas2.getContext('2d');

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
							ctx.drawImage(img_sec,originX,originY,conf.captchaData.params.jigsawWidth,conf.captchaData.params.jigsawHight);
						}
					}
				}

				var btnCtx = function(offX,move,loading){
					ctx2.clearRect(0,0,canvas2.width,canvas2.height);

					ctx2.beginPath();
					ctx2.lineWidth = 2;
					ctx2.rect(0,1,offX,tipHeight-2);
					if(move){
						ctx2.strokeStyle = '#1E90FF';
						ctx2.fillStyle = '#87CEFA';
					}else{
						ctx2.strokeStyle = '#DCDCDC';
						ctx2.fillStyle = '#DCDCDC';						
					}
					ctx2.fill();
					ctx2.stroke();

					ctx2.beginPath();
					ctx2.lineWidth = 2;
					ctx2.rect(offX,1,conf.bgWidth-offX,tipHeight-2);
					ctx2.strokeStyle = '#D3D3D3';
					ctx2.fillStyle = '#DCDCDC';
					ctx2.fill();
					ctx2.stroke();


					ctx2.beginPath();
					ctx2.lineWidth = 2;
					
					ctx2.rect(offX,1,conf.captchaData.params.jigsawWidth,tipHeight-2);
					if(move){
						ctx2.strokeStyle = '#1E90FF';
						ctx2.fillStyle = '#1E90FF';
					}else{
						ctx2.strokeStyle = '#A9A9A9';
						ctx2.fillStyle = '#FFF5EE';
					}
					ctx2.fill();
					ctx2.stroke();
					
					ctx2.beginPath();
					var tt = offX;
					ctx2.lineWidth = 2;
					ctx2.moveTo(tt+15,tipHeight/2);
					ctx2.lineTo(tt+conf.captchaData.params.jigsawWidth-15,tipHeight/2);
					ctx2.lineTo(tt+conf.captchaData.params.jigsawWidth-18,tipHeight/2+4);

					ctx2.moveTo(tt+conf.captchaData.params.jigsawWidth-15,tipHeight/2);
					ctx2.lineTo(tt+conf.captchaData.params.jigsawWidth-18,tipHeight/2-4);

					if(move){
						ctx2.strokeStyle = "#FFFFFF";
					}else{
						ctx2.strokeStyle = "#A9A9A9";
					}
					ctx2.stroke();

					if(!move && !loading){
						ctx2.textAlign="center";
						ctx2.textBaseline="middle";
						ctx2.fillStyle = "#000000";
						ctx2.fillText("向右拖动滑块填充拼图", conf.bgWidth/2, tipHeight/2);
					}
					
				};

				btnCtx(originX,false,false);

				//conf.parContent.appendChild(canvas);

				var table = conf.unit.createElement("table");
				var tr = conf.unit.createElement("tr");
				var td = conf.unit.createElement("td");

				td.appendChild(canvas);
				tr.appendChild(td);
				table.appendChild(tr);

				tr = conf.unit.createElement("tr");
				td = conf.unit.createElement("td");
				td.appendChild(canvas2);
				tr.appendChild(td);
				table.appendChild(tr);

				conf.parContent.appendChild(table);	

				var postting = false;

				var postStatus = function(status,callback){
					if(status){
						postting = false;
						if(callback){
							callback();
						}
					}else{
						ctx2.clearRect(0,0,canvas2.width,canvas2.height);

						ctx2.beginPath();
						ctx2.lineWidth = 2;
						
						ctx2.rect(1,1,conf.bgWidth-2,tipHeight-2);
						ctx2.strokeStyle = 'red';
						ctx2.fillStyle = '#FFF5EE';
						ctx2.fill();
						ctx2.stroke();

						
						ctx2.textAlign="center";
						ctx2.textBaseline="middle";
						ctx2.fillStyle = "red";
						ctx2.fillText("验证失败", conf.bgWidth/2, tipHeight/2);
						
						
						if(callback){
							setTimeout(function(){
							callback();
							},800);
						}
						
					}
					
				}

				canvas2.onmousedown = function(e){
						var finalX;
						canvas2.onmousemove = function(e){
							if(postting){
								return;
							}
							finalX = e.offsetX;
							chkX();
							ctx.clearRect(0,0,canvas.width,canvas.height);
							ctx.drawImage(img,0,0);
							conf.unit.imgdecode(ctx,conf);
							ctx.drawImage(img_sec,finalX,originY,conf.captchaData.params.jigsawWidth,conf.captchaData.params.jigsawHight);

							btnCtx(finalX,true,false);
						};

						var chkX = function(){
							if (finalX >= (canvas.width - conf.captchaData.params.jigsawWidth))
							{
								finalX = canvas.width - conf.captchaData.params.jigsawWidth;
							}
							if(finalX<originX){
								finalX = originX;
							}

						}

						var init = function(){
							if(postting){
								return;
							}
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
										btnCtx(tempCurr,false,false);
									} else {
										ctx.clearRect(0,0,canvas.width,canvas.height);
										ctx.drawImage(img,0,0);
										conf.unit.imgdecode(ctx,conf);
										ctx.drawImage(img_sec,tempCurr,originY,conf.captchaData.params.jigsawWidth,conf.captchaData.params.jigsawHight);

										btnCtx(tempCurr,false,true);
									}
									
								}, 30);
							}else{
								ctx.clearRect(0,0,canvas.width,canvas.height);
								ctx.drawImage(img,0,0);
								conf.unit.imgdecode(ctx,conf);
								ctx.drawImage(img_sec,originX,originY,conf.captchaData.params.jigsawWidth,conf.captchaData.params.jigsawHight);

							
								btnCtx(originX,false,false);
							}
							
							canvas2.onmousemove = null;
							canvas2.onmouseup = null;
							canvas2.onmouseout = null;
							
						};

						canvas2.onmouseup = function(){						
							
							if(finalX){			
								postting = true;
								callbak(JSON.stringify(finalX),postStatus);
							}
							init();
						};


						canvas2.onmouseout = function(){						
							init();
						};
				}
			};
			return {name:name,build:build};
};

module.exports = jigsaw();
