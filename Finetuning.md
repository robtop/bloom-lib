## Adjustable parameters ##

**public int blurPasses = 1**
  * This is quite performance hog parameter but sometimes more blur is just needed to get enough glow.

**public void setTreshold(float treshold)**
  * Treshold for bright parts. Everything under treshold is clamped to 0 treshold must be in range 0..1

**public void setBloomIntesity(float intensity)**
  * Set intensity for bloom. higher mean more brightening for spots that are over treshold. Intensity is multiplier for blurred texture in combining phase. Must be positive.

**public void setOriginalIntesity(float intensity)**
  * set intensity for original scene. under 1 mean darkening and over 1 means lightening. Intensity is multiplier for captured texture in combining phase. Must be positive.

**public void setClearColor(float r, float g, float b, float a)**
  * Set clearing color for capturing buffer. Rarely needed but there for just in case.