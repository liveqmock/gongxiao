var gulp = require("gulp"),
    less = require('gulp-less'),
    runSequence = require('run-sequence'),
    rev = require('gulp-rev'),
    revCollector = require('gulp-rev-collector'),
    uglify =require('gulp-uglify'),
    gutil = require('gulp-util');

//定义css、js源文件路径
var cssSrc = './main/css/**/*.css',
    jsSrc = './main/js/**/*.js';

//CSS生成文件hash编码并生成 rev-manifest.json文件名对照映射
gulp.task('revCss', function() {
    return gulp.src(cssSrc)
        .pipe(rev())
        .pipe(rev.manifest())
        .pipe(gulp.dest('rev/css'));
});

//js生成文件hash编码并生成 rev-manifest.json文件名对照映射
gulp.task('revJs', function() {
    return gulp.src(jsSrc)
        .pipe(rev())
        .pipe(rev.manifest())
        .pipe(gulp.dest('rev/js'));
});

//Html替换css、js文件版本
gulp.task('revHtml', function() {
    return gulp.src(['rev/**/*.json', './**/*.html'])
        .pipe(revCollector())
        .pipe(gulp.dest('./'));
});

//开发构建
gulp.task('dev', function(done) {
    condition = false;
    runSequence(
        ['revCss'], ['revJs'], ['revHtml'],
        done);
});

gulp.task('testLess', function () {
    gulp.src(['./less/*.less'])
        .pipe(less())
        .pipe(gulp.dest('./main/css/'));
});

gulp.task('miniJS',function () {
    gulp.src([jsSrc,'!./js/yhcity/*.js','!./js/*.min.js','!./js/yhgongxiao-d7c02c89e3.js','!./js/bootstrap-*.js'])
        .pipe(uglify({
            mangle:true,
            compress: true
        }))
        .on('error', function (err) {
            gutil.log(gutil.colors.red('[Error]'), err.toString());
        })
        .pipe(gulp.dest('./main/js/'))
});

gulp.task('watchLess', function () {
    // gulp.watch('./js/*.js',['uglifyJS']);
    gulp.watch('./less/*.less', ['testLess']);
    //当所有less文件发生改变时，调用testLess任务
});


gulp.task('default', ['watchLess']);


gulp.task('default', ['dev']);