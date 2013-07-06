<%--
  Created by IntelliJ IDEA.
  User: Joseph Lee <josel@pdx.edu>
  Date: 7/5/13
  Time: 3:54 PM
--%>

<meta name="layout" content="upload"/>

<g:uploadForm action="upload" class="form-horizontal">
    <div class="control-group">
        <label class="control-label" for="userSequenceFile">
            Sequence File
        </label>
        <div class="controls">
            <input type="File" id="userSequenceFile" name="userSequenceFile" required
                   style="background-color: rgba(0, 0, 0, 0); border: 0;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="userOrganism">
            Organism Name
        </label>
        <div class="controls">
            <input type="text" name="userOrganism" id="userOrganism"
                   placeholder="Enter scientific name" required/>
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <input type="submit" />
        </div>
    </div>
</g:uploadForm>