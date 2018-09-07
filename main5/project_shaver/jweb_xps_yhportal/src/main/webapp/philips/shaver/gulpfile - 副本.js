// 安装依赖
var gulp = require('gulp'),
    less = require('gulp-less'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify');

gulp.task("uglifyJS", function () {
    // 把1.js和2.js合并压缩为main.js，输出到目录下
    gulp.src(['./js/bootstrap.min.js','./js/bootstrap-table.js','./js/bootstrap-table-zh-CN.js','./js/select2.min.js','./js/icon-svg.js'])
        .pipe(concat('bootstrap-select-icon.js'))
        .pipe(uglify())
        .pipe(gulp.dest('./js/'));
});


gulp.task('testLess', function () {
    // gulp.src(['./css/**/*.less'])
    // gulp.src(['./css/sales/*.less'])
    gulp.src(['./less/*.less'])
        .pipe(less())
        .pipe(gulp.dest('./css/'));
});

gulp.task('testWatch', function () {
    // gulp.watch('./js/*.js',['uglifyJS']);
    gulp.watch('./less/*.less', ['testLess']);
    //当所有less文件发生改变时，调用testLess任务
});

gulp.task('default', ['testWatch']);