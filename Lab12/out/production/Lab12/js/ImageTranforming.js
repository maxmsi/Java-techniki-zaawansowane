function sepia(img, width, height) {
    for (var y = 0; y < width; y++) {
        for (var x = 0; x < height; x++) {
            var p = img.getRGB(x,y);

            var a = (p>>24)&0xff;
            var r = (p>>16)&0xff;
            var g = (p>>8)&0xff;
            var b = p&0xff;

            //calculate tr, tg, tb
            var tr = (0.393*r + 0.769*g + 0.189*b);
            var tg = (0.349*r + 0.686*g + 0.168*b);
            var tb = (0.272*r + 0.534*g + 0.131*b);

            //check condition
            if(tr > 255){
                r = 255;
            }else{
                r = tr;
            }

            if(tg > 255){
                g = 255;
            }else{
                g = tg;
            }

            if(tb > 255){
                b = 255;
            }else{
                b = tb;
            }

            //set new RGB value
            p = (a<<24) | (r<<16) | (g<<8) | b;

            img.setRGB(x, y, p);
        }
    }

    return img;
}

function usunCzerwony(img,width,height) {
  for(var i=0; i < width; i++)
  {
    for(var j=0; j < height; j++)
    {
      var color = new java.awt.Color(img.getRGB(i, j));
      var newColor = new java.awt.Color(0, color.getGreen(), color.getBlue());
      img.setRGB(i, j, newColor.getRGB());
    }
  }
  return img;
};

function cien(img,width,height) {
  for(var i=0; i < height; i++)
  {
    for(var j=0; j < width; j++)
    {
      var color = new java.awt.Color(img.getRGB(i, j));
      var R = color.getRed()
      var G = color.getGreen()
      var B = color.getBlue()

      if( R - 25 < 0) R = 0
      else R = R - 25

      if( G - 25 < 0) G = 0
      else G = G - 25

      if( B - 25 < 0) B = 0
      else B = B - 25

      var newColor = new java.awt.Color(R.intValue(), G.intValue(), B.intValue());
      img.setRGB(i, j, newColor.getRGB());
    }
  }
  return img;
};

function skalaSzarosci(img, width, height) {
    for (var y = 0; y < width; y++) {
        for (var x = 0; x < height; x++) {
            var p = img.getRGB(x,y);

            var a = (p>>24)&0xff;
            var r = (p>>16)&0xff;
            var g = (p>>8)&0xff;
            var b = p&0xff;

            //calculate average
            var avg = (r+g+b)/3;

            //replace RGB value with avg
            p = (a<<24) | (avg<<16) | (avg<<8) | avg;

            img.setRGB(x, y, p);
        }
    }

    return img;
}