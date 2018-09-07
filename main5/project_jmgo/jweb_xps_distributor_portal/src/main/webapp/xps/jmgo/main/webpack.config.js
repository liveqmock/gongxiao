const path = require('path');
const webpack = require('webpack');
const WebpackMd5Hash = require('webpack-md5-hash');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const UglifyJsPlugin = require('webpack/lib/optimize/UglifyJsPlugin');

const workDir = process.cwd();

module.exports = {
    entry: {
        'app': './mainentry.js'
    },

    output: {
        path: path.join(process.cwd(), './distributor/js/'),
        filename: '[name]-[chunkhash:10].js',
        chunkFilename: '[name]-[chunkhash:10].js'
    },

    resolve: {
        extensions: ['.ts', '.tsx', '.js', '.jsx']
    },

    module: {
        rules: [{
                test: /\.less$/,
                use: [
                    'style-loader',
                    'css-loader?modules&localIdentName=[name]--[local]-[hash:base64:5]',
                    {
                        loader: 'postcss-loader',
                        options: {
                            ident: 'postcss',
                            plugins: (loader) => [
                                require('autoprefixer')({
                                    browsers: ['last 2 versions']
                                }),
                                require('cssnano')()
                            ]
                        }
                    },
                    'less-loader'
                ]
            },
            {
                test: /\.css$/,
                use: [
                    'style-loader',
                    'css-loader?modules&localIdentName=[name]--[local]-[hash:base64:5]',
                    {
                        loader: 'postcss-loader',
                        options: {
                            ident: 'postcss',
                            plugins: (loader) => [
                                require('autoprefixer')({
                                    browsers: ['last 2 versions']
                                }),
                                require('cssnano')()
                            ]
                        }
                    }
                ]
            },
            {
                test: /\.(png|jpg|gif)$/,
                use: ['url-loader?limit=8192']
            },
            {
                test: /\.(woff|eot|ttf|otf|svg|woff2)$/,
                use: ['url-loader?limit=8192']
            },
            {
                test: /\.jsx?$/,
                include: [
                    path.join(workDir, './jquery/'),
                ],
                use: [{
                    loader: 'babel-loader',
                    options: {
                        cacheDirectory: true
                    }
                }]
            }
        ]
    },

    externals: {
        'jquery': 'jQuery',
        'bootstrap': 'bootstrap',
        'bootstrap-table': 'bootstrapTable'
    },

    plugins: [
        new WebpackMd5Hash(),

        new CleanWebpackPlugin(['js'], {
            root: path.join(workDir, 'dist/js'),
            verbose: true,
            dry: false
        }),

        new UglifyJsPlugin({
            sourceMap: false,
            output: {
                comments: false,
            },
            compress: {
                warnings: false
            }
        }),
        new webpack.DefinePlugin({
            'process.env': {
                NODE_ENV: JSON.stringify('development')
            }
        })
    ]
};