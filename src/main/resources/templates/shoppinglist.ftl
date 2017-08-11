[#ftl]
[#import "spring.ftl" as spring/]
[#assign xhtmlCompliant = true in spring/]
[#import "include/snacksPageTemplate.ftl" as page]
[#escape x as x?html]
    [@page.pageTemplate "Snafoo"]
    <div>
        <div class="wrapper">
            <div class="content" role="main">
                <div class="shelf shelf_5">
                    <h2 class="hdg hdg_1">Shopping List</h2>
                </div>
                <div class="shelf shelf_1">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Snack Name</th>
                            <th scope="col">Purchase Location</th>
                        </tr>
                        </thead>
                        <tbody>
                        [#list topTenVotes as top]
                        <tr>
                            <td>${top.name}</td>
                            <td>${top.purchaseLocations}</td>
                        </tr>
                        [/#list]

                        </tbody>
                    </table>
                </div>
            </div>
            <!-- /content -->
        </div>
        <!-- /wrapper -->
    [/@page.pageTemplate]
[/#escape]