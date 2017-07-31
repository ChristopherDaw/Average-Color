#!/bin/zsh
# Variables
TOTALTIME=0
uTOTALTIME=0
REPS=5
FORCE=false

# Handle user input for repitition
while getopts :r:f repititions; do
    case $repititions in
        r)
            REPS=$OPTARG
            shift 2
            ;;
        f)
            FORCE=true
            shift 1
            ;;
        \?)
            continue
            ;;
    esac
done

COMMAND=$@

# Test that the command works
eval $COMMAND > /dev/null
EXIT=$?
if [ "$FORCE" != true ] ; then
    if [ "$EXIT" -eq "1" ] ; then
        printf "The command \`$COMMAND\` throws an error.\n"
        printf "Exiting with code 1.\n"
        exit 1
    fi
fi

# Alert user that the command is running
printf "Running \`$COMMAND\` %d times.\n" $REPS

# Run and time the command given to the script
for i in `seq 1 $REPS`; do
    ELAPSED=$( {/usr/bin/time --format="%e" $@ > /dev/null} 2>&1 )

    TOTALTIME=$(($ELAPSED+$TOTALTIME))
done

# Find average and report
printf "Total run time: %.2f seconds\n" $TOTALTIME
AVGTIME=$(($TOTALTIME/$REPS))
printf "Averate time: %.2f seconds\n" $AVGTIME
