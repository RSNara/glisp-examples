const path = require('path');

module.exports = {
  entry: './src/index.js',
  output: {
    path: path.join(__dirname, 'dest'),
    filename: 'bundle.js',
  },
  module: {
    loaders: [{
      test: /\.js$/,
      exclude: /node_modules|dest/,
      loaders: ['babel', 'eslint'],
    }, {
      test: /\.cljs/,
      loader: 'raw-loader',
    }],
  },
};
