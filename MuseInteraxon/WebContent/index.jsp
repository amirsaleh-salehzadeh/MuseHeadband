<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="stylesheet" type="text/css" media="all" href="css/reset.css" /> <!-- reset css -->
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<style>
    body{ background-color: ivory; }
    #canvas{border:1px solid red;}
</style>
<script>
$(function(){

    var canvas=document.getElementById("canvas");
    var ctx=canvas.getContext("2d");

    // capture incoming socket data in an array
    var data=[];

    // TESTING: fill data with some test values
    for(var i=0;i<5000;i++){
        data.push(Math.sin(i/10)*70+100);
    }

    // x is your most recent data-point in data[]
    var x=0;

    // panAtX is how far the plot will go rightward on the canvas
    // until the canvas is panned

    var panAtX=250;

    var continueAnimation=true;
    animate();


    function animate(){

        if(x>data.length-1){return;}

        if(continueAnimation){
            requestAnimationFrame(animate);
        }

        if(x++<panAtX){

            ctx.fillRect(x,data[x],1,1);

        }else{

            ctx.clearRect(0,0,canvas.width,canvas.height);

            // plot data[] from x-PanAtX to x 

            for(var xx=0;xx<panAtX;xx++){
                var y=data[x-panAtX+xx];
                ctx.fillRect(xx,y,1,1)
            }
        }
    }


}); // end $(function(){});
</script>
</head>
<body>
    <canvas id="canvas" width=300 height=300></canvas>
</body>
</html>