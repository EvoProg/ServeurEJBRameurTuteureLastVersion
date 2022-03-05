import { AnimationMixer } from 'https://cdn.skypack.dev/three@0.135.0/src/animation/AnimationMixer.js';
import { AnimationUtils } from 'https://cdn.skypack.dev/three@0.135.0/src/animation/AnimationUtils.js';

function setupModel(data) {
    let weight = 0;
    let push = false;

    const additiveActions = {
        horizontale: { weight: 0},
        rotation_z: { weight: 0}
    };

    const allActions = [];

    //get model from scene
    const model = data.scene.children[0];

    const mixer = new AnimationMixer(model);

    let numAnimations = data.animations.length;

    //get animations
    for (let i = 0; i !== numAnimations; ++ i ) {

        //get clip animations
        let clip = data.animations[ i ];
        const name = clip.name;

        //format animations changement
        AnimationUtils.makeClipAdditive( clip );

        //get subclip from the clip to avoid the entire clip
        //REGARDER 
        clip = AnimationUtils.subclip( clip, clip.name, 38, 39, 30);

        //add clip to the mixer
        const action = mixer.clipAction( clip );

        //play action
        activateAction( action, additiveActions );

        additiveActions[ name ].action = action;
        allActions.push( action );

        //add keypress event
        const log = document.getElementById('scene-container');
        document.addEventListener('keydown', increase_weight);
        document.addEventListener('keyup', decrease_weight);
        function increase_weight() {
                push = true;
        }
        function decrease_weight() {
                push = false;
        }
        
    }

    //model animation
    model.tick = (delta) => {
        for( let i = 0; i !== numAnimations; ++ i ) {
            const action = allActions[ i ];
            const clip = action.getClip();
            const settings = additiveActions[ clip.name ];

            //weight action updating (with slider)
//            setWeight(settings.action, get_weight(clip.name));

            //weight action updating (with key press)
            if ( push && weight < 1) {
                weight += 0.01;
            }
            else if ( !push && weight > 0 ){
                weight -= 0.01;            
            }
            setWeight(settings.action, weight);

            settings.weight = action.getEffectiveWeight();  
        }

        mixer.update(delta);
    }

    return model;
}

function get_weight(id) {
    var weight = document.getElementById(id).value / 100;
    return weight;
}

function activateAction( action, additiveActions ) {
    const clip = action.getClip();
    const settings = additiveActions[ clip.name ];
    setWeight( action, settings.weight );
    action.play();
}

function setWeight( action, weight ) {
    action.enabled = true;
    action.setEffectiveTimeScale( 1 );
    action.setEffectiveWeight( weight );
}


export { setupModel };
