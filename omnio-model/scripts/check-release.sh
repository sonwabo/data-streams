#!/usr/bin/env bash
EC=0
if [[ "$email" == "" ]] ; then echo "Environment variable email must be provided" ; EC=1 ; fi
if [[ "$SCM_REMOTE_URL" == "" ]] ; then echo "Environment variable SCM_REMOTE_URL must be provided" ; EC=1 ; fi
if [[ "$SCM_USERNAME" == "" ]] ; then echo "Environment variable SCM_USERNAME must be provided" ; EC=1 ; fi
if [[ "$SCM_PASSWORD" == "" ]] ; then echo "Environment variable SCM_PASSWORD must be provided" ; EC=1 ; fi
exit $EC
