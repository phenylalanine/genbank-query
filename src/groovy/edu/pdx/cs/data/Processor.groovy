package edu.pdx.cs.data

import org.biojavax.bio.seq.RichSequence

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 6/30/13
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Processor {
    public void process(RichSequence richSequence);
}