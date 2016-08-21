import 'babel-polyfill';
import * as GLISP from 'glisp';
import library from './library.cljs';
import program from './index.cljs';
import * as Redux from 'redux';
import * as R from 'ramda';
import * as M from 'mathjs';
import * as I from 'immutable';
import * as VirtualDOM from 'virtual-dom';

window.I = I;
window.M = M;
window.R = R;

const LIBRARIES = {
  [Symbol.for('Redux')]: Redux,
  [Symbol.for('VirtualDOM')]: VirtualDOM,
};

run(LIBRARIES, `(do ${library} ${program})`);

function run(env, code) {
  return GLISP.evaluate(create(GLISP.RootEnv, env), GLISP.parse(code));
}

function create(proto, props) {
  return Object.assign(Object.create(proto), props);
}
