package com.stormeye.event.store.services.storage.block.domain;

import com.casper.sdk.model.common.Digest;
import com.casper.sdk.model.key.PublicKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stormeye.event.store.conveter.DigestConverter;
import com.stormeye.event.store.conveter.PublicKeyConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.Date;

/**
 * The domain object for a Block
 *
 * @author ian@meywood.com
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Block extends AbstractPersistable<Long> {

    @Convert(converter = DigestConverter.class)
    @Column
    private Digest blockHash;
    @Convert(converter = DigestConverter.class)
    @Column
    private Digest parentHash;
    /** ISO Date */
    @Column
    private Date timestamp;
    @Column
    @Convert(converter = DigestConverter.class)
    private Digest state;
    @Column
    private long deployCount;
    @Column
    private long transferCount;
    @Column
    private long eraId;
    @Column
    @Convert(converter = PublicKeyConverter.class)
    private PublicKey proposer;
    @Column
    private long blockHeight;
}
