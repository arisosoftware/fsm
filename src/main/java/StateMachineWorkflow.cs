using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Workflow
{ 
 
    public enum PolicyPermssionEnum
    {
        NoPermission = 0, ReadOnly = 1, ReadWrite = 2, CanCreate = 3
    }

    public enum UserRoles
    {
        CCSASenior = 0, Reviewer = 1, LendingManager = 2, CountryLimitReviewer = 3, ReviewerWithAuthorization, CAIROReadOnly, CountryLimitReviewerReadOnly
    }


    public enum DocumentEditingStates
    {
        None = 0, Edit = 1, ReadOnly = 2, EditReviewCommentsOnly = 3 
    }

    public enum WorkflowCommand
    {
        None = 0, View = 1, Save = 2, SendTo = 3, Submit = 4, Return = 5, Decline = 6, Approve = 7,
        TransferToMe = 8, Recall = 9, Delete = 10, NewFromProcess = 11
    }


    public enum PolicyWorkflowStatusEnum
    {
        Draft = 1, PendingApproval = 2, Approved = 3, Declined = 4, UnderReview = 5,
    }


    public class HeaderInfoData
    {
        public string InfoKey { get; set; }
        public string InfoValue { get; set; }

        public HeaderInfoData(string key, string value)
        {
            InfoKey = key;
            InfoValue = value;
        }
    }


       
    public class PolicyWorkflow
    {
        #region setupworkflow




        class WorkflowState
        {
            public int WorkflowStatusId;
            public string WorkflowStatusName;


            public List<WorkflowTransition> transits = new List<WorkflowTransition>();

            public bool HasPermission(WorkflowCommand cmd, UserRoles userrole)
            {
                if (transits.Count(u => u.command == cmd && u.userrole == userrole) > 0)
                    return true;
                else
                    return false;
            }

            public bool HasPermission(WorkflowCommand cmd, List<int> allroles)
            {
                foreach (UserRoles r in allroles)
                {
                    if (HasPermission(cmd, r))
                    {
                        return true;
                    }
                }
                return false;
            }


            public bool HasPermission(WorkflowCommand cmd, List<UserRoles> allroles)
            {
                foreach (UserRoles r in allroles)
                {
                    if (HasPermission(cmd, r))
                    {
                        return true;
                    }
                }
                return false;
            }


            public List<UserRoles> WorkflowCmdTargetRoleList(WorkflowCommand cmd)
            {
                WorkflowState next = this.transits.First(u => u.command == cmd).nextState;
                return next.transits.Where(u => u.command == WorkflowCommand.Save).Select(u => u.userrole).ToList();
            }
              

            public WorkflowState GetNextState(WorkflowCommand cmd)
            {
                return this.transits.First(u => u.command == cmd).nextState;
            }

        }

        class WorkflowTransition
        {
            public UserRoles userrole;
            public WorkflowCommand command;
            public WorkflowState nextState;
        }


        private static Dictionary<int, WorkflowState> _states;
        private static Dictionary<int, WorkflowState> StateMachine
        {
            get
            {
                if (_states == null)
                {
                    SetupPolicyWorkflowMachine();
                }

                return _states;
            }
            set
            {

            }
        }

        static void BuildTransition(UserRoles userrole, WorkflowCommand cmd, WorkflowState from, WorkflowState to)
        {
            WorkflowTransition tran = new WorkflowTransition();
            tran.command = cmd;
            tran.nextState = to;
            tran.userrole = userrole;
            from.transits.Add(tran);

        }

        static void BuildTransition(UserRoles userrole, WorkflowCommand cmd, WorkflowState state)
        {
            WorkflowState from = state;
            WorkflowState to = state;
            BuildTransition(userrole, cmd, from, to);
        }


        /// <summary>
        /// setup the policy workflow and permssions.
        /// </summary>
        public static void SetupPolicyWorkflowMachine()
        {
            if (PolicyWorkflow._states == null)
            {
                Dictionary<int, WorkflowState> inner_status_dic = new Dictionary<int, WorkflowState>();

                WorkflowState Draft = new WorkflowState()
                {
                    WorkflowStatusId = (int)PolicyWorkflowStatusEnum.Draft,
                    WorkflowStatusName = "Draft"
                };
                inner_status_dic.Add(Draft.WorkflowStatusId, Draft);

                WorkflowState PendingApproval = new WorkflowState()
                {
                    WorkflowStatusId = (int)PolicyWorkflowStatusEnum.PendingApproval,
                    WorkflowStatusName = "Pending Approval"
                };
                inner_status_dic.Add(PendingApproval.WorkflowStatusId, PendingApproval);


                WorkflowState Approved = new WorkflowState()
                {
                    WorkflowStatusId = (int)PolicyWorkflowStatusEnum.Approved,
                    WorkflowStatusName = "Approved"
                };
                inner_status_dic.Add(Approved.WorkflowStatusId, Approved);

                WorkflowState Declined = new WorkflowState()
                {
                    WorkflowStatusId = (int)PolicyWorkflowStatusEnum.Declined,
                    WorkflowStatusName = "Declined"
                };
                inner_status_dic.Add(Declined.WorkflowStatusId, Declined);


                WorkflowState UnderReview = new WorkflowState()
                {
                    WorkflowStatusId = (int)PolicyWorkflowStatusEnum.UnderReview,
                    WorkflowStatusName = "Under Review"
                };
                inner_status_dic.Add(UnderReview.WorkflowStatusId, UnderReview);


                //(PolicyCommand.[a-zA-Z]*,) ([a-z][A-Z]*,) ([a-z][A-Z]*,) (UserRoles.[a-zA-Z]*) ->  \2 \3 \1 \4

                // EnableTransferToMe
                {
                    BuildTransition(UserRoles.LendingManager, WorkflowCommand.TransferToMe, Draft);
                    BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.TransferToMe, Draft);
                    BuildTransition(UserRoles.Reviewer, WorkflowCommand.TransferToMe, Draft);

                    BuildTransition(UserRoles.ReviewerWithAuthorization, WorkflowCommand.TransferToMe, UnderReview);
                    BuildTransition(UserRoles.ReviewerWithAuthorization, WorkflowCommand.TransferToMe, PendingApproval);

                }


                BuildTransition(UserRoles.LendingManager, WorkflowCommand.Save, Draft);
                BuildTransition(UserRoles.LendingManager, WorkflowCommand.SendTo, Draft);

                BuildTransition(UserRoles.LendingManager, WorkflowCommand.Submit, Draft, PendingApproval);
                BuildTransition(UserRoles.LendingManager, WorkflowCommand.Recall, PendingApproval, Draft);
                BuildTransition(UserRoles.LendingManager, WorkflowCommand.Delete, Draft);
                BuildTransition(UserRoles.LendingManager, WorkflowCommand.NewFromProcess, Approved);
                BuildTransition(UserRoles.LendingManager, WorkflowCommand.NewFromProcess, Declined);

                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.Save, Draft);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.SendTo, Draft);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.Submit, Draft, PendingApproval);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.Recall, PendingApproval, Draft);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.Delete, Draft);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.NewFromProcess, Approved);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.NewFromProcess, Declined);

                BuildTransition(UserRoles.Reviewer, WorkflowCommand.Save, Draft);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.SendTo, Draft);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.Submit, Draft, PendingApproval);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.Recall, PendingApproval, Draft);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.NewFromProcess, Approved);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.NewFromProcess, Declined);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.Delete, Draft);

                // PendingApproval only Authorization can work on it.
                BuildTransition(UserRoles.ReviewerWithAuthorization, WorkflowCommand.SendTo, PendingApproval);

                BuildTransition(UserRoles.ReviewerWithAuthorization, WorkflowCommand.Save, PendingApproval, UnderReview);
                BuildTransition(UserRoles.ReviewerWithAuthorization, WorkflowCommand.Save, UnderReview);
                BuildTransition(UserRoles.ReviewerWithAuthorization, WorkflowCommand.SendTo, UnderReview);

                BuildTransition(UserRoles.ReviewerWithAuthorization, WorkflowCommand.Approve, UnderReview, Approved);
                BuildTransition(UserRoles.ReviewerWithAuthorization, WorkflowCommand.Decline, UnderReview, Declined);
                BuildTransition(UserRoles.ReviewerWithAuthorization, WorkflowCommand.Return, UnderReview, Draft);
                 

                // all view only can do..

                BuildTransition(UserRoles.CAIROReadOnly, WorkflowCommand.View, Approved);
                BuildTransition(UserRoles.CAIROReadOnly, WorkflowCommand.View, Declined);
                BuildTransition(UserRoles.CAIROReadOnly, WorkflowCommand.View, Draft);
                BuildTransition(UserRoles.CAIROReadOnly, WorkflowCommand.View, UnderReview);
                BuildTransition(UserRoles.CAIROReadOnly, WorkflowCommand.View, PendingApproval);

                BuildTransition(UserRoles.LendingManager, WorkflowCommand.View, Approved);
                BuildTransition(UserRoles.LendingManager, WorkflowCommand.View, Declined);
                BuildTransition(UserRoles.LendingManager, WorkflowCommand.View, Draft);
                BuildTransition(UserRoles.LendingManager, WorkflowCommand.View, UnderReview);
                BuildTransition(UserRoles.LendingManager, WorkflowCommand.View, PendingApproval);

                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.View, Approved);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.View, Declined);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.View, Draft);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.View, UnderReview);
                BuildTransition(UserRoles.CountryLimitReviewer, WorkflowCommand.View, PendingApproval);

                BuildTransition(UserRoles.CountryLimitReviewerReadOnly, WorkflowCommand.View, Approved);
                BuildTransition(UserRoles.CountryLimitReviewerReadOnly, WorkflowCommand.View, Declined);
                BuildTransition(UserRoles.CountryLimitReviewerReadOnly, WorkflowCommand.View, Draft);
                BuildTransition(UserRoles.CountryLimitReviewerReadOnly, WorkflowCommand.View, UnderReview);
                BuildTransition(UserRoles.CountryLimitReviewerReadOnly, WorkflowCommand.View, PendingApproval);

                BuildTransition(UserRoles.Reviewer, WorkflowCommand.View, Approved);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.View, Declined);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.View, Draft);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.View, UnderReview);
                BuildTransition(UserRoles.Reviewer, WorkflowCommand.View, PendingApproval);
 
 

                BuildTransition(UserRoles.CCSASenior, WorkflowCommand.View, Approved);
                BuildTransition(UserRoles.CCSASenior, WorkflowCommand.View, Declined);
                BuildTransition(UserRoles.CCSASenior, WorkflowCommand.View, Draft);
                BuildTransition(UserRoles.CCSASenior, WorkflowCommand.View, UnderReview);
                BuildTransition(UserRoles.CCSASenior, WorkflowCommand.View, PendingApproval);

                PolicyWorkflow._states = inner_status_dic;


              //   System.Diagnostics.Debug.WriteLine(ExportWorkflowChartInDOTLang());
            }
        }


        /*
                public static string shortRoleName(UserRoles role)
                {
                    switch(role)
                    {
                        case UserRoles.CCSAJunior: return "CCSAjr";  ;
                        case UserRoles.CCSASenior: return "CCSA";  ;
                        case UserRoles.LendingManager: return "LM";  ;
                        case UserRoles.CAIROReadOnly: return "ReadOnly";  ;
                        case UserRoles.CountryLimitReviewer: return "CLR";  ;
                        case UserRoles.CountryLimitReviewerReadOnly: return "CLR_Readonly";  ;
                        case UserRoles.Reviewer: return "Reviewer";  ;
                        case UserRoles.ReviewerWithAuthorization: return "Authorizer";  
                        case UserRoles.SystemAdministrator: return "Admin"; 

                    }
                    return "N/A";
                }

                // debug only.
                //
                public static string ExportWorkflowChartInDOTLang()
                {
                    StringBuilder vis = new StringBuilder();
                    vis.AppendLine("\n//================\n\n");
                    vis.Append("// workflow state list\n var states = [");
                    //input: Dictionary<int, WorkflowState> states
                    foreach(var stateItem in _states)
                    { 
                        vis.AppendFormat("\"{0}\", ", stateItem.Value.WorkflowStatusName);
                    }
                    vis.AppendLine(" ];\n\n");
                    vis.AppendLine(" states.forEach(function(state) { g.setNode(state, { label: state }); }); \n\n");

                    //g.setEdge("DRAFT",     "Pending",     { label: "submit" });
                    foreach (var stateItem in _states)
                    {
                        WorkflowState src = stateItem.Value;

                  List<WorkflowState> targetLst = src.transits.Select(u => u.nextState).Distinct().ToList();

                        foreach(var targetRow in targetLst)
                        {
                            StringBuilder groupEdgeLabel = new StringBuilder();

                            PolicyCommand lastCommand = PolicyCommand.None ;
                            foreach (var edge in src.transits.Where(u=>u.nextState == targetRow && u.command!= PolicyCommand.View ).OrderBy(u=>u.command))
                            {
                                // then each line have one command
                                PolicyCommand cmd = edge.command;

                                if (cmd!=lastCommand)
                                {
                                    lastCommand = cmd;
                                    if (groupEdgeLabel.Length > 1)
                                        groupEdgeLabel.Append("\\n \\n");

                                    groupEdgeLabel.Append(cmd.ToString() + ":");
                                }

                                string Label = "\\n"+shortRoleName(edge.userrole) ;

                                groupEdgeLabel.Append(Label);

                            }

                            string edgeDefine = string.Format("g.setEdge(\"{0}\",\t\"{1}\",\t[ label: \"{2}\" ]); ", src.WorkflowStatusName, targetRow.WorkflowStatusName, groupEdgeLabel.ToString());
                            edgeDefine = edgeDefine.Replace("[", "{");
                            edgeDefine = edgeDefine.Replace("]", "}");

                            vis.AppendLine(edgeDefine);
                        }


                    }

                    vis.AppendLine("\n//================\n\n");


                    return vis.ToString(); ;
                }
              */


        #endregion

        public int AssigneeUserId;
        public int CurrWorkflowStatusId;

        public static PolicyWorkflow Create()
        {
            PolicyWorkflow hp = new PolicyWorkflow();
            hp.CurrWorkflowStatusId = 1;
            return hp;
        }


        public static PolicyWorkflow Create(int workflowstatusCode)
        {
            PolicyWorkflow hp = new PolicyWorkflow();
            hp.CurrWorkflowStatusId = workflowstatusCode;
            return hp;
        }

        public string GetWorkflowStautsString()
        {
            WorkflowState curr = StateMachine[(int)CurrWorkflowStatusId];
            return curr.WorkflowStatusName;
        }
         


        public static string GetWorkflowImageSource(byte workflowcode)
        {
            switch (workflowcode)
            {
                case (int)PolicyWorkflowStatusEnum.Draft:
                    return "pack://application:,,,/Resources/Graphics/HomePage/CreditDrat.png";

                case (int)PolicyWorkflowStatusEnum.PendingApproval:
                    return "pack://application:,,,/Resources/Graphics/HomePage/CreditSubmitted.png";

                case (int)PolicyWorkflowStatusEnum.Approved:
                    return "pack://application:,,,/Resources/Graphics/HomePage/CreditProcessed.png";

                case (int)PolicyWorkflowStatusEnum.Declined:
                    return "pack://application:,,,/Resources/Graphics/HomePage/CreditCanceled.png";

                case (int)PolicyWorkflowStatusEnum.UnderReview:
                    return "pack://application:,,,/Resources/Graphics/HomePage/CreditUnderReview.png";


            }
            return string.Empty;
        }

        public static List<UserRoles> SendTo_UserRoleList(WorkflowCommand cmd, byte workflowstatusCode)
        {
            WorkflowState curr = StateMachine[(int)workflowstatusCode];
            return curr.WorkflowCmdTargetRoleList(cmd);
        }


        static WorkflowState GetWorkflowState(byte workflowstatusCode)
        {
            WorkflowState curr = StateMachine[(int)workflowstatusCode];
            return curr;
        }

        // public static string ExecuteWorkflow(CreditPolicyT modified, SecurityUserT user)
        public static string ExecuteWorkflow(  int CurrWorkflowStat, WorkflowCommand cmd, List<int> userSecurityUserRole_Id_List)
        
        {
            WorkflowState curr = StateMachine[(int)CurrWorkflowStat];
            WorkflowState next = curr;
       

            if (curr.HasPermission(cmd, userSecurityUserRole_Id_List))
            {
                if (curr.transits.First(u => u.command == cmd) != null)
                {
                    next = curr.GetNextState(cmd);
                    CurrWorkflowStat = (byte)next.WorkflowStatusId;
                    return next.WorkflowStatusName;
                }
                else
                {
                    return "No Permission..";
                }
            }
            else
            {
                return "No Permission.";
            }
            return string.Empty;
        }


        /// <summary>
        /// validate before send request to server side.
        /// </summary>
        /// <param name="modified"></param>
        /// <param name="user"></param>
        /// <returns></returns>
        public static string ValidateWorkflow(int CurrWorkflowStat, WorkflowCommand cmd, List<int> userSecurityUserRole_Id_List)
        {
            WorkflowState curr = StateMachine[(int)CurrWorkflowStat];
            WorkflowState next = curr;
                        
            StringBuilder errormessge = new StringBuilder();

            if (curr.HasPermission(cmd, userSecurityUserRole_Id_List))
            {
                if (curr.transits.First(u => u.command == cmd) != null)
                {
                    if (cmd == WorkflowCommand.Submit)
                    {
                        //if (modified.PurposeComments == null)
                        //{
                        //    errormessge.Append( "\u2022 Purpose Comment is Mandatory \n");
                        //}
                    }                            
                }
                else
                {
                    return "No Permission..";
                }
            }
            else
            {
                return "No Permission.";
            }
            return errormessge.ToString();
        }
         
         

    }
}
