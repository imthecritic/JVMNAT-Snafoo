[#ftl]
[#import "spring.ftl" as spring/]
[#assign xhtmlCompliant = true in spring/]
[#import "include/snacksPageTemplate.ftl" as page]
[#escape x as x?html]
    [@page.pageTemplate "Snafoo"]
    <script type="text/javascript">

    </script>
    <div>
        <div class="wrapper">
            <div class="content" role="main">
                <div class="shelf shelf_5">
                    <h2 class="hdg hdg_1">Suggestions</h2>
                </div>

                <div class="content-centered">
                    <div class="shelf shelf_2">
                        <form action="/suggestion.htm" modelattribute="sug" method="post">

                            <fieldset class="shelf shelf_2">
                                <div class="shelf shelf_2">
                                    <div class="shelf">
                                        <label for="suggestions">
                                            <h2 class="hdg hdg_2">Select a snack from the list</h2>
                                        </label>
                                    </div>
                                    <select name="suggestions"  id="suggestions">
                                        <option value="default">Please Select One</option>
                                        [#list suggestions as suggestion]
                                        <option value="${suggestion}" name=${suggestion.name}>${suggestion.name}</option>
                                        [/#list]
                                    </select>
                                </div>
                            </fieldset>
                            <div class="shelf shelf_5">
                                <p class="hdg hdg_1">or</p>
                            </div>
                            <fieldset class="shelf shelf_5">
                                <div class="shelf">
                                    <label for="suggestionInput">
                                        <h2 class="hdg hdg_2">Enter new snack suggestion &amp; purchasing location</h2>
                                    </label>
                                </div>
                                <div class="shelf">
                                    <label for="suggestionLocation" class="isHidden">Suggestion</label>
                                    <input type="text" id="suggestionInput" name="name"   placeholder="Snack Suggestion" />
                                </div>
                                <div class="shelf">
                                    <label for="suggestionLocation" class="isHidden">Location</label>
                                    <input type="text" id="suggestionInput" name="location" placeholder="Purchase Location" />
                                </div>
                            </fieldset>
                            <input type="submit" value="Suggest this Snack!" class="btn" >
                        </form>
                        <br>
                        [#if message == "" ]
                            <label></label>
                        [#else]
                            <label><font color="red">${message}</font></label>
                        [/#if]
                    </div>
                </div>
            </div>
            <!-- /content -->
        </div>
        <!-- /wrapper -->
    [/@page.pageTemplate]
[/#escape]