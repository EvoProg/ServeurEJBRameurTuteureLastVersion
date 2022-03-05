import { PerspectiveCamera } from 'https://cdn.skypack.dev/three@0.135.0/src/cameras/PerspectiveCamera.js';

function createCamera() {
  const camera = new PerspectiveCamera(35, 1, 0.1, 100);

  camera.position.set(-20, 10, 6.5);

  return camera;
}

export { createCamera };

