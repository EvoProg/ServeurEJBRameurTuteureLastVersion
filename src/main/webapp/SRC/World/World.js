import { loadCube } from './components/cube/cube.js';
import { createCamera } from './components/camera.js';
import { createLights } from './components/lights.js';
import { createScene } from './components/scene.js';

import { createControls } from './systems/controls.js';
import { createRenderer } from './systems/renderer.js';
import { Resizer } from './systems/resizer.js';
import { Loop } from './systems/loop.js';

import { PlaneGeometry } from 'https://cdn.skypack.dev/three@0.135.0/src/geometries/PlaneGeometry.js';
import { MeshBasicMaterial } from 'https://cdn.skypack.dev/three@0.135.0/src/materials/MeshBasicMaterial.js';
import { Mesh } from 'https://cdn.skypack.dev/three@0.135.0/src/objects/Mesh.js';

let camera;
let controls;
let renderer;
let scene;
let loop;

class World {
    constructor(container) {
        camera = createCamera();
        renderer = createRenderer();
        scene = createScene();
        container.append(renderer.domElement);
        controls = createControls(camera, renderer.domElement);
        loop = new Loop(camera, scene, renderer);
        const ambiantLight = createLights();
        scene.add(ambiantLight);
        loop.updatables.push(controls);

        const resizer = new Resizer(container, camera, renderer);
    }

    async init() {
        const { bateau, anneau_gauche, anneau_droit, aviron_gauche, aviron_droit, /*assise,*/ avatar } = await loadCube();
        loop.updatables.push(anneau_gauche, anneau_droit, aviron_gauche, aviron_droit, /*assise,*/ avatar);
        scene.add(bateau, anneau_gauche, anneau_droit, aviron_gauche, aviron_droit, /*assise,*/ avatar);

        const geometry = new PlaneGeometry(24, 3);
        const material = new MeshBasicMaterial({color: 0x0080ff});
        const plane = new Mesh(geometry, material);
        plane.rotation.x = - 3.14 / 2;
        plane.position.x += 9;

        scene.add(plane);
    }

    render() {
        renderer.render(scene,camera);
    }

    start() {
        loop.start();
    }

    stop() {
        loop.stop();
    }
}

export { World };

