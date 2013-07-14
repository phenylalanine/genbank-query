package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence

/**
 * Created with IntelliJ IDEA.
 * User: Laura
 * Date: 7/11/13
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UProcessor {
    public ProcessedUploadedSequence process(RichSequence richSequence);
}