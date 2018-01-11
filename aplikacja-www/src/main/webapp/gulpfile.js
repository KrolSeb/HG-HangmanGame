var gulp = require('gulp'),
    sass = require('gulp-sass'),
    autoprefixer = require('gulp-autoprefixer');

// sass & autoprefixer
gulp.task('sass|autoprefixer', function () {
    return gulp.src('./sass/style.scss')
        .pipe(sass({ outputStyle: 'compressed' }).on('error', sass.logError))
        .pipe(autoprefixer({ cascade: false }))
        .pipe(gulp.dest('./css'));
});

gulp.task('sass|autoprefixer:watch', function () {
    return gulp.watch('./sass/**/*.scss', ['sass|autoprefixer']);
});


// default task
gulp.task('default', ['sass|autoprefixer:watch']);
