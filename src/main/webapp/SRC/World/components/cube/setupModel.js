import { AnimationMixer } from 'https://cdn.skypack.dev/three@0.135.0/src/animation/AnimationMixer.js';
import { AnimationUtils } from 'https://cdn.skypack.dev/three@0.135.0/src/animation/AnimationUtils.js';
import { AnimationClip } from 'https://cdn.skypack.dev/three@0.135.0/src/animation/AnimationClip.js';
import { SkeletonHelper } from 'https://cdn.skypack.dev/three@0.135.0/src/helpers/SkeletonHelper.js';

function setupModel(data) {

    let weight_verticale = 0;
    let weight_horizontale = 0;
    let weight_pousse_1 = 0;
    let weight_pousse_2 = 0;
    let vitesse = 0;
    let push = false;

    const allActions = [];

    //get models from scene
    const bateau = data.scene.getObjectByName('Bateau');
    const anneau_gauche = data.scene.getObjectByName('Anneau_Gauche');
    const aviron_droit = data.scene.getObjectByName('Aviron_Droit');
    const anneau_droit = data.scene.getObjectByName('Anneau_Droit');
    const aviron_gauche = data.scene.getObjectByName('Aviron_Gauche');
    //const assise = data.scene.getObjectByName('Assise');
    const avatar = data.scene.getObjectByName('avatar').parent;

    const models = [bateau, anneau_gauche, aviron_droit, anneau_droit, aviron_gauche, /*assise,*/ avatar];

    console.log(data.animations);

    //creation model mixers
    const mixer_aviron_droit = new AnimationMixer(aviron_droit);
    const mixer_anneau_droit = new AnimationMixer(anneau_droit);
    const mixer_aviron_gauche = new AnimationMixer(aviron_gauche);
    const mixer_anneau_gauche = new AnimationMixer(anneau_gauche);
    //const mixer_assise = new AnimationMixer(assise);
    const mixer_avatar = new AnimationMixer(avatar);

    //Animations avirons
    init_model_animation("Action_horizontale_aviron_droit", mixer_aviron_droit, allActions, data, 58, 59);
    init_model_animation("Action_verticale_aviron_droit", mixer_aviron_droit, allActions, data, 58, 59);
    init_model_animation("Action_horizontale_aviron_gauche", mixer_aviron_gauche, allActions, data, 58, 59);
    init_model_animation("Action_verticale_aviron_gauche", mixer_aviron_gauche, allActions, data, 58, 59);
    init_model_animation("Anneau_droit_rotation", mixer_anneau_droit, allActions, data, 58, 59);
    init_model_animation("Anneau_gauche_rotation", mixer_anneau_gauche, allActions, data, 58, 59);
    //init_model_animation("Action_horizontal_assise", mixer_assise, allActions, data, 58, 59);

    //Animations avatar
    init_model_animation("pousse_horizontal", mixer_avatar, allActions, data, 30, 31);

    //recuperation de la deuxieme partie de pousse horizontal de l animation (30-60)
    let clip = AnimationClip.findByName(data.animations, "pousse_horizontal");
    AnimationUtils.makeClipAdditive( clip );
    clip = AnimationUtils.subclip( clip, clip.name, 30, 60, 30);
    let clip_final = clip;
    AnimationUtils.makeClipAdditive( clip_final );
    clip_final = AnimationUtils.subclip( clip_final, clip_final.name, 28, 29, 30);
    const action_test = mixer_avatar.clipAction( clip_final);
    //play action
    activateAction( action_test );
    allActions.push( action_test );

    init_model_animation("pousse_vertical_bras", mixer_avatar, allActions, data, 58, 59);

    console.log(allActions.length);

    //add keypress event
    document.addEventListener('keydown', increase_weight);
    document.addEventListener('keyup', decrease_weight);

    function increase_weight() {
            push = true;
    }
    function decrease_weight() {
            push = false;
    }

    //models animations
    aviron_droit.tick = (delta) => {
        let action_aviron_horizontale_droit = allActions[0];
        let action_aviron_verticale_droit = allActions[1];

        //weight action updating (with key press)
        if ( push && weight_horizontale < 1) {
            weight_horizontale += 0.01;
            if( weight_verticale < 1) {
                weight_verticale += 0.2;
            }
        }
        else if ( !push && weight_horizontale > 0 ) {
            weight_horizontale -= 0.01;
            if( weight_verticale > 0) {
                weight_verticale -= 0.2;
            }
        }

        vitesse = moving_models(weight_horizontale, push, models, delta, vitesse);

        setWeight(action_aviron_horizontale_droit, weight_horizontale);
        setWeight(action_aviron_verticale_droit, weight_verticale);

        mixer_aviron_droit.update(delta);
    }

    aviron_gauche.tick = (delta) => {
        let action_aviron_horizontale_gauche = allActions[2];
        let action_aviron_verticale_gauche = allActions[3];

        setWeight(action_aviron_horizontale_gauche, weight_horizontale);
        setWeight(action_aviron_verticale_gauche, weight_verticale);

        mixer_aviron_gauche.update(delta);
    }

    anneau_droit.tick = (delta) => {
        let action_anneau_droit = allActions[4];

        setWeight(action_anneau_droit, weight_horizontale);

        mixer_anneau_droit.update(delta);
    }

    anneau_gauche.tick = (delta) => {
        let action_anneau_gauche = allActions[5];

        setWeight(action_anneau_gauche, weight_horizontale);

        mixer_anneau_gauche.update(delta);
    }

    /*assise.tick = (delta) => {
        let action_assise = allActions[6];

        setWeight(action_assise, weight_horizontale);

        mixer_assise.update(delta);
    }*/

    avatar.tick = (delta) => {
        let action_avatar_pousse_jambes_part_1 = allActions[6];
        let action_avatar_pousse_jambes_part_2 = allActions[7];
        let action_avatar_pousse_bras = allActions[8];

        if ( weight_horizontale > 0 && weight_horizontale <= 0.5) {
            if(push)
            {
                weight_pousse_1 += 0.02;
            }
            else
            {
                weight_pousse_1 -= 0.02;
            }
        }

        if ( weight_horizontale > 0.5 && weight_horizontale < 1) {
            if(push)
            {
                weight_pousse_2 += 0.02;
            }
            else
            {
                weight_pousse_2 -= 0.02;
            }
        }

        setWeight(action_avatar_pousse_jambes_part_1, weight_pousse_1);
        setWeight(action_avatar_pousse_jambes_part_2, weight_pousse_2);
        setWeight(action_avatar_pousse_bras, weight_verticale);

        mixer_avatar.update(delta);
    }

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

function init_model_animation(animation_name, mixer, allActions, data, start, end) {
    //get clip animations
    let clip = AnimationClip.findByName(data.animations, animation_name);

    //format animations changement
    AnimationUtils.makeClipAdditive( clip );

    //get subclip from the clip to avoid the entire clip
    //REGARDER
    clip = AnimationUtils.subclip( clip, clip.name, start, end, 30);

    //add clip to the mixer
    const action = mixer.clipAction( clip );

    //play action
    activateAction( action );

    allActions.push( action );
}

function activateAction( action ) {
    setWeight(action,0);
    action.play();
}

function setWeight( action, weight ) {
    action.enabled = true;
    action.setEffectiveTimeScale( 1 );
    action.setEffectiveWeight( weight );
}

function moving_models(weight, push, models, delta, vitesse)
{
    if(weight < 1 && push)
    {
         vitesse += weight;
    }

    if(vitesse > 0)
    {
        vitesse -= vitesse * vitesse * 0.002;
    }

    for(var i = 0; i < models.length; i++)
    {
        models[i].position.x += vitesse * delta;
    }

    return vitesse;
}

export { setupModel };
