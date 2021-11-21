package service;

import entity.Album;
import entity.Singer;

import java.util.List;

public interface AlbumService {

    List<Album> findAll();

    List<Album> findBySinger(Singer singer);

    List<Album> findByTitle(String title);
}