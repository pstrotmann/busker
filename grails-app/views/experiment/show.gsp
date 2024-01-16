<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'experiment.label', default: 'Experiment')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#show-experiment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                        <li><g:link class="controller" controller="PlaceToPlay" action="index" >PlacesToPlay</g:link></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                        <li><g:link class="controller" controller="Experiment" action="run1" id="${this.experiment.id}">1 round</g:link></li>
                        <li><g:link class="controller" controller="Experiment" action="run10" id="${this.experiment.id}">10 rounds</g:link></li>
                        <li><g:link class="controller" controller="Experiment" action="run100" id="${this.experiment.id}">100 rounds</g:link></li>
                        <li><g:link class="controller" controller="Experiment" action="run1000" id="${this.experiment.id}">1000 rounds</g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="show-experiment" class="col-12 content scaffold-show" role="main">
                    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <f:display bean="experiment" />
                    <ul>
                        <li><g:message message="maxReward:" /><f:display bean="experiment" property="maxReward"/></li>
                    </ul>
                    <g:form resource="${this.experiment}" method="DELETE">
                        <fieldset class="buttons">
                            <g:link class="edit" action="edit" resource="${this.experiment}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                            <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                        </fieldset>
                    </g:form>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>
