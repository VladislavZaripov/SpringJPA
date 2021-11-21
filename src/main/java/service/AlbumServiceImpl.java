package service;

import entity.Album;
import entity.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AlbumRepository;

import java.util.List;

@Service("jpaAlbumrService")
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Album> findBySinger(Singer singer) {
        return albumRepository.findBySinger(singer);
    }

    @Transactional(readOnly = true)
    public List<Album> findByTitle(String title) {
        return albumRepository.findByTitle(title);
    }
}