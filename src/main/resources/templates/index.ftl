[#ftl]
[#import "spring.ftl" as spring/]
[#assign xhtmlCompliant = true in spring/]
[#import "include/snacksPageTemplate.ftl" as page]
[#escape x as x?html]
    [@page.pageTemplate "Snafoo"]
    <div>
        <form:hidden path="/userCookie"/>
        <div class="wrapper">
            <div class="content" role="main">
                <div class="shelf shelf_5">
                    <h1 class="hdg hdg_1">Voting</h1>
                </div>
                <div class="shelf shelf_2">
                    <p>You are able to vote for up to three selections each month.</p>
                </div>
                <div class="shelf shelf_2">
                    <div class="voteBox">
                        <div class="voteBox-hd">
                            <h2 class="hdg hdg_3">Votes Remaining</h2>
                        </div>
                        <div class="voteBox-body">
                            [#if userVoteModel.getNumOfVotesLeft() == 3]
                                <p class="counter counter_green ">3</p>
                            [#elseif userVoteModel.getNumOfVotesLeft() == 2]
                                <p class="counter counter_yellow">2</p>
                            [#elseif userVoteModel.getNumOfVotesLeft() == 1]
                                <p class="counter counter_red ">1</p>
                            [#elseif userVoteModel.getNumOfVotesLeft() == 0]
                                <p class="counter counter_red ">0</p>
                            [/#if]
                        </div>
                    </div>
                </div>
                <div class="shelf shelf_2">
                    <p class="error isHidden">Opps! You have already voted the total allowed times this month.<br />Come back next month to vote again!</p>
                </div>
                <div class="split">
                    <div class="shelf shelf_2">
                        <div class="shelf">
                            <h2 class="hdg hdg_2 mix-hdg_centered ">Snacks Always Purchased</h2>
                        </div>
                        <ul class="list list_centered">
                            [#list alwaysOrdered  as always]
                                <li>${always.name}</li>
                            [/#list]
                        </ul>
                    </div>
                </div>
                <div class="split">
                    <div class="shelf shelf_2">
                        <div class="shelf">
                            <h2 class="hdg hdg_2 mix-hdg_centered ">Snacks suggested this month</h2>
                        </div>
                        <div class="shelf shelf_5">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Snack Food</th>
                                    <th scope="col">Current Votes</th>
                                    <th scope="col">VOTE</th>
                                    <th scope="col">Last Date Purchased</th>
                                </tr>
                                </thead>
                                <tbody>
                                <form action="/vote.htm" modelattribute="vote" method="post" class="btn btn_clear">
                                    [#list optional  as option]
                                        <tr>
                                            <td>${option.name}</td>
                                            <td>${option.getVotes()}</td>
                                            <td>
                                                [#if userVoteModel.votedForSnack(option) ]
                                                    <button type="submit" value="${option}" name="vote" class=="button" >
                                                        <i class="icon-check icon-check_voted"></i>
                                                    </button>
                                                [#else ]
                                                    <button type="submit" value="${option}" name="vote" class=="button">
                                                        <i class="icon-check icon-check_noVote"></i>
                                                    </button>
                                                [/#if]
                                            </td>
                                            <td>${option.lastPurchaseDate}</td>
                                        </tr>
                                    [/#list]
                                </form>
                                </tbody>
                            </table>
                        </div>
                        <p> Snacks you've voted for this month:
                        [#list userVoteModel.getSnackList() as snackVoted] ${snackVoted.name} [#if snackVoted_has_next],[/#if][/#list]</p>
                    </div>
                </div>
            </div>
            <!-- /content -->
        </div>
        <!-- /wrapper -->
    [/@page.pageTemplate]
[/#escape]