// 安装依赖
var gulp = require('gulp'),
	less = require('gulp-less');

gulp.task('testLess',function(){
	gulp.src(['./distributor/less/*.less'])
        .pipe(less())
        .pipe(gulp.dest('./distributor/css/'));
});

gulp.task('testWatch',function(){
    gulp.watch('./distributor/less/*.less',['testLess']);
    //当所有less文件发生改变时，调用testLess任务
});

gulp.task('default',['testWatch']);