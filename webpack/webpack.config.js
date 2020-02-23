const path = require('path')
const uglifyjs = require('uglifyjs-webpack-plugin');

module.exports = {
  //mode: "production", //打包为生产模式
  mode: "none", //打包为生产模式
  entry: [
	  "./src/core.js"
	  ],
  output: {
    filename: 'captcha.js',   //打包后的名称
	library: 'jsonljd',
    libraryTarget: 'this',
    path: path.resolve(__dirname, 'dist')  //打包后的输出到该目录下
  },
  externals: {
        lodash: {
            commonjs: 'lodash',
            commonjs2: 'lodash',
            amd: 'lodash',
            root: '_'
        }
    },
  performance: {     // 关闭提示
        hints: false
  },
			/*
  optimization: {
            minimize: false,
            minimizer: [
                new uglifyjs()
            ]
  },*/
  plugins: [
	  new uglifyjs()
  ]
}