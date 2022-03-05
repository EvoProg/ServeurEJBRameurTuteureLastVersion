import { Scene } from 'https://cdn.skypack.dev/three@0.135.0/src/scenes/Scene.js';
import { Color } from 'https://cdn.skypack.dev/three@0.135.0/src/math/Color.js';

function createScene() {
    const scene = new Scene();

    scene.background = new Color( 0xeeeeee );

    return scene;
}

export { createScene };

