import { animation, animate, style, keyframes } from '@angular/animations';

export const cartAnimation = animation([
    animate('0.8s', keyframes([
        style({ boxShadow: '0 0 0 0px rgba({{boxShadow}}, 0.5)', background: '{{background}}', offset: 0 }),
        style({ boxShadow: '0 0 0 35px rgba({{boxShadow}}, 0)', background: '#576582b3', offset: 0.8 })
    ]))
]);