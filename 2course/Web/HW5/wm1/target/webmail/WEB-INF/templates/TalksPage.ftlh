<#import "common.ftl" as c>

<@c.page>
    <form method="post" action="/talks" id="send-message-form">
        <input type="hidden" name="action" value="sendMessage">
        <label for="message-text">Message Text</label>
        <textarea name="targetUser" form="send-message-form"
                  title="Target">Enter target user...</textarea>
        <textarea name="message-text" form="send-message-form"
                  title="Message Text">Enter message here...</textarea>
        <input type="submit" value="Send">
        <#list talks as talk>
            <div class="talk">
                ${talk.creationTime} | ${talk.sourceUser} → ${talk.targetUser}:
                ${talk.message}
            </div>
        </#list>
        <#if message??>
            ${message}
        </#if>
    </form>
</@c.page>
