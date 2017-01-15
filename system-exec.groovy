import hudson.model.*
import hudson.model.ParametersAction
import hudson.model.StringParameterValue
import hudson.tasks.Shell
import hudson.cli.CreateNodeCommand

ParametersAction action = build.getAction(ParametersAction.class)
StringParameterValue spv = action.getParameters()[0]
def user_choice_sha = spv.getValue()
def sha = build.getEnvVars()["GIT_COMMIT"].substring(0,7)
if(user_choice_sha != 'HEAD'){
  sha = user_choice_sha.substring(0,7)
}
println('Proceeding with SHA : ' + sha)

/*def shell = new Shell('/usr/bin/ansible-playbook -i inv dockerize.yml')
println(shell.getContents())*/
def sout = new StringBuffer(), serr = new StringBuffer()
def ipaddr = '192.168.1.141'
def cmd = '/bin/bash run.sh ' + sha + ' ' + ipaddr
//def cmd = '/usr/bin/ansible-playbook -i inv dockerize.yml'
println "Executing: " + cmd
def proc = cmd.execute(null, new File("/var/lib/jenkins/workspace/local-dev-env-trigger-manual"))
proc.text.eachLine {println it}
proc.consumeProcessOutput(sout, serr)
//proc.waitForOrKill(40000)
println sout

