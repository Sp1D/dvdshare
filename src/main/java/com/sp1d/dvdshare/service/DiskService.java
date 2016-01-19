/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.service;

import com.sp1d.dvdshare.entities.Disk;
import com.sp1d.dvdshare.repos.DiskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sp1d
 */
@Service
@Transactional
public class DiskService {

    @Autowired
    DiskRepo diskRepo;
    
    public Disk add(Disk user) {
        return diskRepo.add(user);
    }

    public Disk save(Disk user) {
        return diskRepo.save(user);
    }

}
