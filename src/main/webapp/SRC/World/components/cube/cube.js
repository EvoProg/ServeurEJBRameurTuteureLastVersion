import { GLTFLoader } from 'https://cdn.skypack.dev/three@0.135.0/examples/jsm/loaders/GLTFLoader.js';

import { setupModel } from './setupModel.js';

async function loadCube() {
    const loader = new GLTFLoader();
    const cubeData = await loader.loadAsync('/ServeurEJBRameurTutore-1.0-SNAPSHOT/ASSETS/models/aviron_avatar_animation.glb');
    const {bateau, anneau_gauche, anneau_droit, aviron_gauche, aviron_droit, /*assise,*/ avatar} = setupModel(cubeData);

    //return cube;
    return {
        bateau,
        anneau_gauche,
        anneau_droit,
        aviron_gauche,
        aviron_droit,
        /*assise,*/
        avatar
    };
}

export {loadCube};