import 'babel-polyfill';
import * as GLISP from 'glisp';
import library from './library.cljs';
import program from './index.cljs';

GLISP.run(`(do ${library} ${program})`);
