import { AmbientLight } from 'https://cdn.skypack.dev/three@0.135.0/src/lights/AmbientLight.js';
import { HemisphereLight } from 'https://cdn.skypack.dev/three@0.135.0/src/lights/HemisphereLight.js';

function createLights() {
    //const light = new AmbientLight( 0x404040,5 );
  const light = new HemisphereLight(
    'white',
    'darkslategrey',
    5,
  );
    return light;
}

export { createLights };
