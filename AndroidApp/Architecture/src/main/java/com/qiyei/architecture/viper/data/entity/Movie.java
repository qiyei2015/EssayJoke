package com.qiyei.architecture.viper.data.entity;

/**
 * @author Created by qiyei2015 on 2020/2/16.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class Movie {

    private int voteCount;
    private int id;
    private boolean video;
    private float voteAverage;
    private String title;
    private float popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private int genreIds;
    private String backdropPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private boolean watched;

    /**
     * @return {@link #voteCount}
     */
    public int getVoteCount() {
        return voteCount;
    }

    /**
     * @param voteCount the {@link #voteCount} to set
     */
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the {@link #id} to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return {@link #video}
     */
    public boolean isVideo() {
        return video;
    }

    /**
     * @param video the {@link #video} to set
     */
    public void setVideo(boolean video) {
        this.video = video;
    }

    /**
     * @return {@link #voteAverage}
     */
    public float getVoteAverage() {
        return voteAverage;
    }

    /**
     * @param voteAverage the {@link #voteAverage} to set
     */
    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * @return {@link #title}
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the {@link #title} to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return {@link #popularity}
     */
    public float getPopularity() {
        return popularity;
    }

    /**
     * @param popularity the {@link #popularity} to set
     */
    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    /**
     * @return {@link #posterPath}
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @param posterPath the {@link #posterPath} to set
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * @return {@link #originalLanguage}
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * @param originalLanguage the {@link #originalLanguage} to set
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * @return {@link #originalTitle}
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * @param originalTitle the {@link #originalTitle} to set
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * @return {@link #genreIds}
     */
    public int getGenreIds() {
        return genreIds;
    }

    /**
     * @param genreIds the {@link #genreIds} to set
     */
    public void setGenreIds(int genreIds) {
        this.genreIds = genreIds;
    }

    /**
     * @return {@link #backdropPath}
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * @param backdropPath the {@link #backdropPath} to set
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     * @return {@link #adult}
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * @param adult the {@link #adult} to set
     */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    /**
     * @return {@link #overview}
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview the {@link #overview} to set
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return {@link #releaseDate}
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the {@link #releaseDate} to set
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return {@link #watched}
     */
    public boolean isWatched() {
        return watched;
    }

    /**
     * @param watched the {@link #watched} to set
     */
    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
