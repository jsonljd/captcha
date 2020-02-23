function point(){
			var name = "POINT_CLICK_HANDLER";
			var build = function(conf,callbak){
				var canvas = conf.unit.createCanvas(conf.bgWidth,conf.bgHight);
				var ctx = canvas.getContext('2d');

				var canvas2 = conf.unit.createCanvas(conf.bgWidth,28);
				var ctx2 = canvas2.getContext('2d');

				var isize = conf.captchaData.params.i_s;	
				var img = new Image();			
				var img_sec = new Image();
				img.setAttribute('crossOrigin', 'anonymous');
				img_sec.setAttribute('crossOrigin', 'anonymous');
				var xqo = conf.captchaData.data;
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
							ctx2.drawImage(img_sec,0,0,conf.bgWidth,28);
						}
					}
				}	
				var table = conf.unit.createElement("table");
				var tr = conf.unit.createElement("tr");
				var td = conf.unit.createElement("td");

				td.appendChild(canvas2);
				tr.appendChild(td);
				table.appendChild(tr);

				tr = conf.unit.createElement("tr");
				td = conf.unit.createElement("td");
				td.appendChild(canvas);
				tr.appendChild(td);
				table.appendChild(tr);

				conf.parContent.appendChild(table);

				var pos = 0;
				var drawRic = function(ctx, data, option,text) {
					var cood = option.cood;
					var radius = option.radius;
					var lastpos = pos = -58;
					for (var i = 0; i < data.length; i++) {
					ctx.beginPath();
					ctx.moveTo(cood.x, cood.y);	
					ctx.fillStyle = data[i].bgcolor;
					pos = lastpos + Math.PI * 2 * data[i].value / 100;
					ctx.arc(cood.x, cood.y, radius, lastpos, pos, false);
					ctx.fill();
					lastpos = pos;
					}
					ctx.beginPath();
					ctx.fillStyle = "white";
					radius *= 0.9;
					ctx.arc(cood.x, cood.y, radius, 0, Math.PI * 2, false);
					ctx.fill();
					ctx.beginPath();
					ctx.font = "bold 10pt Courier";
					ctx.textAlign="center";
					ctx.textBaseline="middle";
					ctx.fillStyle = 'red';
					ctx.stroke();
					ctx.fillText(text,cood.x, cood.y);
				}

				var cliindex = 0;
				var clkarr = [];
				var iswait = false;
				var data = [
				{ bgcolor: 'pink', value: 100 },
				{ bgcolor: 'red', value: 100 }
				];
				canvas.onclick = function(e){
					if(iswait){
						return;
					}
					var obj = {};
					obj["x"] = e.offsetX;
					obj["y"] = e.offsetY;
					clkarr["push"](obj);

					cliindex+=1;
					drawRic(ctx, data, { cood: { x: e.offsetX, y: e.offsetY }, radius: 10 },cliindex);
					if( Math.ceil(cliindex) >= Math.ceil(isize) ) {
						iswait = true;
						setTimeout(function(){
							callbak(JSON.stringify(clkarr));
							ctx.drawImage(img,0,0);
							cliindex = 0;
							clkarr = [];
							iswait = false;
						},800);						
						return;
					}
				}
				
			};
			return {name:name,build:build};
}

module.exports = point();	