var hexa,
             text1 = 'SCORE'.split(''),
             settings = {
                 size: 150,
                 margin: 12,
                 fontSize: 100,
                 perspective: 450
             },
             makeObject = function(a){
                 var o = {};
                 for(var i = 0, l = a.length; i < l; i++){
                     o['letter' + i] = a;
                 }
                 return o;
             },
             getSequence = function(a, reverse, random){
                 var o = {}, p;
                 for(var i = 0, l = a.length; i < l; i++){
                     if(reverse){
                         p = l - i - 1;
                     }else if(random){
                         p = Math.floor(Math.random() * l);
                     }else{
                         p = i;
                     }
                     o['letter' + i] = a[p];
                 }
                 return o;
             };
         
         document.addEventListener('DOMContentLoaded', function(){
             hexa = new HexaFlip(document.getElementById('cubes'), makeObject(text1), settings);
         
             setTimeout(function(){
                 hexa.setValue(getSequence(text1, true));
             }, 0);
         
             setTimeout(function(){
                 hexa.setValue(getSequence(text1));
             }, 1000);
         
             setTimeout(function(){
                 setInterval(function(){
                     hexa.setValue(getSequence(text1, false, true));
                 }, 3000);
             }, 5000);
         }, false);