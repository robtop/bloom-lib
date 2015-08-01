Keep in mind that bloom is pretty heavyweight post process effect for mobile devices and use about 4-10ms time per frame. If Your game is allready fill rate limited then bloom isn't maybe the best choise then. Good thing is that trying bloom-lib should take about 5minutes at most including everything

## List of optimization that use can do ##

  * se smaller fbo size. half of screen width and height is maximum that is ever needed. Higher than that just cause less blur which is not good thing for bloom

  * isable blending if not needed. Blending is only needed if only some parts of scene is needed to bloomed and that geomerty is rendered after non bloomed ones

  * et 32bits fram buffer to false. This cause awful banding so use this at last resort