import { WebGLRenderer } from 'https://cdn.skypack.dev/three@0.135.0/src/renderers/WebGLRenderer.js';

function createRenderer() {
    //activating antialiasing
    const renderer = new WebGLRenderer({ antialias: true });
    //renderer.physicallyCorrectLights = true;
    //renderer.setSize( window.innerWidth, window.innerHeight );

    return renderer;
}

export { createRenderer };

